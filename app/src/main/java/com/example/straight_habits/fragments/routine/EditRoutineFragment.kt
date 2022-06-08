package com.example.straight_habits.fragments.routine

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.straight_habits.R
import com.example.straight_habits.adapters.routine.EditRoutineAdapter
import com.example.straight_habits.beans.RoutineBean
import com.example.straight_habits.controller.application.ManageRoutine
import com.example.straight_habits.database.RoomDB
import com.example.straight_habits.facade.ManageDaysFacade
import com.example.straight_habits.facade.ManageRoutineFacade
import com.example.straight_habits.fragments.details.RoutineDetailsEditFragment
import com.example.straight_habits.interfaces.routine.UpdateEditedRoutineInterface
import com.example.straight_habits.interfaces.routine.EditRoutineInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.collections.ArrayList


class EditRoutineFragment : Fragment(), EditRoutineInterface, UpdateEditedRoutineInterface {
    //Habits
    private lateinit var rvHabits: RecyclerView
    private lateinit var routineAdapter: EditRoutineAdapter
    private lateinit var habitsList: MutableList<RoutineBean>
    //Category
    //var category: String? = "Morning"



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_routine, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Get Bundle
        var category: String? = "Morning"
        val bundle = arguments

        //Get Category Name
        if(bundle != null){
            category = bundle!!.getString("Category")
            Log.e(String(), category.toString())
        }


        //Set RecyclerView
        rvHabits = view.findViewById(R.id.rv_edit_habits)

        //Using Coroutines to Manage the Room DB
        lifecycleScope.launch(Dispatchers.IO){
            //Habits
            //Get HabitBeans List
            if (category != null) {
                getHabitsList(category)
            }
            //Set the Recycler View
            setHabitsRecyclerView()
        }
    }




    //Habits
    //Get the Habits List
    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getHabitsList(category: String) {
        //Database Instance
        val DB = RoomDB.getInstance(requireContext()).routineDAO()

        //Habits List
        //val habits = DB.readAll(ManageDaysFacade.getCurrentDay())
        val habits = DB.readAllCategory(ManageDaysFacade.getCurrentDay(), category)


        //Get the beans
        habitsList = ArrayList()
        for(i in 0 until habits.size){
            //Create the Bean
            //Add the Bean to the List
            habitsList.add(
                RoutineBean(
                    habits[i].getID(),
                    habits[i].getName(),
                    habits[i].getInformation(),
                    habits[i].getCategory(),
                    habits[i].getStartHour(),
                    habits[i].getEndHour(),
                    habits[i].getSelected(),
                    habits[i].getDone()
                )
            )
        }

        //Order the Habits
        orderHabitsList()
    }

    //Order the Beans by their Starting Hour
    private fun orderHabitsList(){
        var app: RoutineBean

        //Loop to sort the beans by their starting hour
        for(i in 0 until habitsList.size - 1){
            for(j in i until habitsList.size){
                //Tokenizer
                val st1 = StringTokenizer(habitsList[i].getStartHour(), ":")
                val st2 = StringTokenizer(habitsList[j].getStartHour(), ":")

                //Get the Values
                var start1 : MutableList<Int> = ArrayList()
                while(st1.hasMoreTokens())
                    start1.add(st1.nextToken().toInt())

                var start2 : MutableList<Int> = ArrayList()
                while(st2.hasMoreTokens())
                    start2.add(st2.nextToken().toInt())


                //Sort Hours
                if(start1[0] > start2[0]){
                    app = habitsList[i]
                    habitsList[i] = habitsList[j]
                    habitsList[j] = app
                }

                //Sort Minutes
                if(start1[0] == start2[0] && start1[1] > start2[1]){
                    app = habitsList[i]
                    habitsList[i] = habitsList[j]
                    habitsList[j] = app
                }
            }
        }
    }


    //Set Recycler View to display the Habits List
    private fun setHabitsRecyclerView(){
        //Adapter
        routineAdapter = EditRoutineAdapter(habitsList, this)

        //To Update the UI we need to use the UI Thread
        activity?.runOnUiThread {
            //Layout Manager
            rvHabits.layoutManager = LinearLayoutManager(context)
            rvHabits.adapter = routineAdapter
        }
    }





    //Interface Methods-----------------------------------------------------------------------------
    //Edit Habit Interface
    @RequiresApi(Build.VERSION_CODES.O)
    override fun deleteHabit(position: Int) {
        //Application Controller
        val manageHabit = ManageRoutine()

        //Coroutines
        runBlocking {
            //Delete Habit
            val habitModel = ManageRoutineFacade.beanToModel(habitsList[position], ManageDaysFacade.getCurrentDay())
            manageHabit.deleteRoutine(habitModel, requireContext())

            //Check if the Habit was Selected. If so, select the next one that is not done
            if(habitsList[position].getSelected()){
                val i = ManageRoutineFacade.getNotDone(habitsList, position + 1)

                activity?.runOnUiThread {
                    Toast.makeText(context, i.toString(), Toast.LENGTH_SHORT).show()
                }

                if(i != habitsList.size){
                    //Set Selected the first not done bean
                    habitsList[i].setSelected(true)

                    manageHabit
                        .editRoutine(ManageRoutineFacade
                            .beanToModel(habitsList[i], ManageDaysFacade.getCurrentDay()), requireContext())
                }
            }
        }

        //Remove Habit from the List
        habitsList.removeAt(position)

        //Notify the Adapter
        routineAdapter.notifyDataSetChanged()
    }

    override fun editHabit(position: Int) {
        val bundle = Bundle()
        bundle.putSerializable("Edit Habit Details", habitsList[position])

        //Create the Details Fragment
        val habitDetailsEditFragment = RoutineDetailsEditFragment(this)
        habitDetailsEditFragment.arguments = bundle
        activity?.let { habitDetailsEditFragment.show(it.supportFragmentManager,"EditHabitDetailsFragment") }
    }





    //Function Called by the Edit Habit Details fragment
    @RequiresApi(Build.VERSION_CODES.O)
    override fun updateList(position: Int, routine: RoutineBean){
        //Get Bundle
        var category: String? = "Morning"
        val bundle = arguments

        //Get Category Name
        if(bundle != null){
            category = bundle!!.getString("Category")
            Log.e(String(), category.toString())
        }


        //Using Coroutines to Manage the Room DB
        lifecycleScope.launch(Dispatchers.IO){
            //Habits
            //Get HabitBeans List
            if (category != null) {
                getHabitsList(category)
            }
            //Set the Recycler View
            setHabitsRecyclerView()
        }
    }
}