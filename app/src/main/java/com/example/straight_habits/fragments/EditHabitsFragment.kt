package com.example.straight_habits.fragments

import android.os.Build
import android.os.Bundle
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
import com.example.straight_habits.adapters.EditHabitsAdapter
import com.example.straight_habits.beans.HabitBean
import com.example.straight_habits.controller.application.ManageHabits
import com.example.straight_habits.database.RoomDB
import com.example.straight_habits.facade.ManageDaysFacade
import com.example.straight_habits.facade.ManageHabitsFacade
import com.example.straight_habits.fragments.details.HabitDetailsEditFragment
import com.example.straight_habits.fragments.details.HabitDetailsFragment
import com.example.straight_habits.interfaces.EditHabitInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.collections.ArrayList


class EditHabitsFragment : Fragment(), EditHabitInterface {
    //Habits
    private lateinit var rvHabits: RecyclerView
    private lateinit var habitsAdapter: EditHabitsAdapter
    private lateinit var habitsList: MutableList<HabitBean>



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Set RecyclerView
        rvHabits = view.findViewById(R.id.rv_edit_habits)

        //Using Coroutines to Manage the Room DB
        lifecycleScope.launch(Dispatchers.IO){
            //Habits
            //Get HabitBeans List
            getHabitsList()
            //Set the Recycler View
            setHabitsRecyclerView()
        }
    }




    //Habits
    //Get the Habits List
    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getHabitsList() {
        //Database Instance
        val DB = RoomDB.getInstance(requireContext()).habitDAO()

        //Habits List
        val habits = DB.readAll(ManageDaysFacade.getCurrentDay())


        //Get the beans
        habitsList = ArrayList()
        for(i in 0 until habits.size){
            //Create the Bean
            //Add the Bean to the List
            habitsList.add(
                HabitBean(
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
        var app: HabitBean

        //The Empty Beans will be placed in the end of the List
        for(i in 0 until habitsList.size - 1){
            //If the habit is empty it has to in the last places of the List
            if(habitsList[i].getEmpty()){
                //Get last position
                var j = habitsList.size - 1
                while(habitsList[j].getEmpty() && j != i)
                    j--

                //Swap the Habits if they're not the same
                if(i != j){
                    val app = habitsList[i]
                    habitsList[i] = habitsList[j]
                    habitsList[j] = app
                }
            }
        }

        //Loop to sort the beans by their starting hour
        for(i in 0 until habitsList.size - 1){
            for(j in i until habitsList.size){

                if(!habitsList[i].getEmpty() && !habitsList[j].getEmpty()){
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
    }


    //Set Recycler View to display the Habits List
    private fun setHabitsRecyclerView(){
        //Adapter
        habitsAdapter = EditHabitsAdapter(habitsList, this)

        //To Update the UI we need to use the UI Thread
        activity?.runOnUiThread {
            //Layout Manager
            rvHabits.layoutManager = LinearLayoutManager(context)
            rvHabits.adapter = habitsAdapter
        }
    }




    //Interface Methods-----------------------------------------------------------------------------
    //Edit Habit Interface
    @RequiresApi(Build.VERSION_CODES.O)
    override fun deleteHabit(position: Int) {
        //Application Controller
        val manageHabit = ManageHabits()

        //Coroutines
        runBlocking {
            //Delete Habit
            val habitModel = ManageHabitsFacade.beanToModel(habitsList[position], ManageDaysFacade.getCurrentDay())
            manageHabit.deleteHabit(habitModel, requireContext())

            //Check if the Habit was Selected. If so, select the next one that is not done
            if(habitsList[position].getSelected()){
                val i = ManageHabitsFacade.getNotDone(habitsList, position + 1)

                activity?.runOnUiThread {
                    Toast.makeText(context, i.toString(), Toast.LENGTH_SHORT).show()
                }

                if(i != habitsList.size){
                    //Set Selected the first not done bean
                    habitsList[i].setSelected(true)

                    manageHabit
                        .editHabit(ManageHabitsFacade
                            .beanToModel(habitsList[i], ManageDaysFacade.getCurrentDay()), requireContext())
                }
            }
        }

        //Remove Habit from the List
        habitsList.removeAt(position)

        //Notify the Adapter
        habitsAdapter.notifyDataSetChanged()
    }

    override fun editHabit(position: Int) {
        val bundle = Bundle()
        bundle.putSerializable("Edit Habit Details", habitsList[position])

        //Create the Details Fragment
        val habitDetailsEditFragment = HabitDetailsEditFragment()
        habitDetailsEditFragment.arguments = bundle
        activity?.let { habitDetailsEditFragment.show(it.supportFragmentManager,"EditHabitDetailsFragment") }
    }
}