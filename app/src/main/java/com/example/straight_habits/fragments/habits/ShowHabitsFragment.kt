package com.example.straight_habits.fragments.habits

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.straight_habits.R
import com.example.straight_habits.adapters.habits.HabitsAdapter
import com.example.straight_habits.beans.HabitBean
import com.example.straight_habits.controller.application.ManageHabits
import com.example.straight_habits.database.RoomDB
import com.example.straight_habits.facade.ManageDaysFacade
import com.example.straight_habits.facade.ManageHabitsFacade
import com.example.straight_habits.fragments.details.HabitDetailsFragment
import com.example.straight_habits.interfaces.habits.CheckHabitInterface
import com.example.straight_habits.interfaces.habits.HabitDetailsInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.collections.ArrayList


class ShowHabitsFragment : Fragment(), CheckHabitInterface, HabitDetailsInterface {
    //Habits
    private lateinit var rvHabits: RecyclerView
    private lateinit var habitsAdapter: HabitsAdapter
    private lateinit var habitsList: MutableList<HabitBean>



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_habits, container, false)
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
        rvHabits = view.findViewById(R.id.rv_habits)

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
        val DB = RoomDB.getInstance(requireContext()).habitDAO()

        //Habits List
        //val habits = DB.readAll(ManageDaysFacade.getCurrentDay())
        val habits = DB.readAllCategory(ManageDaysFacade.getCurrentDay(), category)


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
        //Select the first one
        if(habitsList.size != 0)
            selectFirstHabit()
    }

    //Order the Beans by their Starting Hour
    private fun orderHabitsList(){
        var app: HabitBean

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

    //Select the first habit
    @RequiresApi(Build.VERSION_CODES.O)
    private fun selectFirstHabit(){
        //Application Controller
        val manageHabits = ManageHabits()

        //Check if the first habit is not selected
        if(!habitsList[0].getDone() && !habitsList[0].getSelected())
            habitsList[0].setSelected(true)

        //Update habit
        runBlocking {
            manageHabits
                .editHabit(
                    ManageHabitsFacade.beanToModel(habitsList[0],
                        ManageDaysFacade.getCurrentDay()),
                    requireContext())
        }
    }


    //Set Recycler View to display the Habits List
    private fun setHabitsRecyclerView(){
        //Adapter
        habitsAdapter = HabitsAdapter(habitsList, this, this)

        //To Update the UI we need to use the UI Thread
        activity?.runOnUiThread {
            //Layout Manager
            rvHabits.layoutManager = LinearLayoutManager(context)
            rvHabits.adapter = habitsAdapter

            //Position
            var position = ManageHabitsFacade.getSelectedPosition(habitsList)
            if(position != 0) position--

            //Show the selected Habit
            rvHabits.scrollToPosition(position)
        }
    }





    //Interface Methods-----------------------------------------------------------------------------
    //Check Habit Interface
    //Check Clicked Habit
    @RequiresApi(Build.VERSION_CODES.O)
    override fun checkHabit(position: Int) {
        //Connect to DB
        val DB = RoomDB.getInstance(requireContext())
        val dao = DB.habitDAO()

        //Loop to set on false the habit.selected
        habitsList[position - 1].setSelected(false)
        //Set on true habit.done
        habitsList[position - 1].setDone(true)

        //Edit the Room DB to deselect and set done the Habit Clicked
        lifecycleScope.launch(Dispatchers.IO){
            dao.edit(ManageHabitsFacade.beanToModel(habitsList[position - 1], ManageDaysFacade.getCurrentDay()))
        }


        //Set on true habit.selected
        var i = position
        while (i != habitsList.size){
            //Next Select Condition
            if(!habitsList[i].getDone()){
                habitsList[i].setSelected(true)

                //Edit the Room DB to select the next Habit that has not be done
                lifecycleScope.launch(Dispatchers.IO){
                    //Edit DB
                    dao.edit(ManageHabitsFacade.beanToModel(habitsList[i], ManageDaysFacade.getCurrentDay()))
                }
                break
            }

            i++
        }

        //Notify the Adapter
        activity?.runOnUiThread{
            habitsAdapter.notifyDataSetChanged()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun checkLastHabit(position: Int) {
        //Connect to DB
        val DB = RoomDB.getInstance(requireContext())
        val dao = DB.habitDAO()

        //Set Selected and Done
        habitsList[position].setSelected(false)
        habitsList[position].setDone(true)

        //Update the DB
        lifecycleScope.launch(Dispatchers.IO){
            //Edit the Database
            dao.edit(ManageHabitsFacade.beanToModel(habitsList[position], ManageDaysFacade.getCurrentDay()))
        }

        //Notify the Adapter
        activity?.runOnUiThread{
            habitsAdapter.notifyDataSetChanged()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun preDoneHabit(position: Int, clicked: Boolean) {
        //Connect to DB
        val DB = RoomDB.getInstance(requireContext())
        val dao = DB.habitDAO()

        //Set Done or Undone
        if(clicked)
            habitsList[position].setDone(true)
        else
            habitsList[position].setDone(false)

        //Update the DB
        lifecycleScope.launch(Dispatchers.IO){
            //Edit DB
            dao.edit(ManageHabitsFacade.beanToModel(habitsList[position], ManageDaysFacade.getCurrentDay()))
        }
    }

    override fun openHabitDetails(position: Int) {
        val bundle = Bundle()
        bundle.putSerializable("Habit Details", habitsList[position])

        //Create the Details Fragment
        val habitDetailsFragment = HabitDetailsFragment()
        habitDetailsFragment.arguments = bundle
        activity?.let { habitDetailsFragment.show(it.supportFragmentManager,"HabitDetailsFragment") }
    }
}