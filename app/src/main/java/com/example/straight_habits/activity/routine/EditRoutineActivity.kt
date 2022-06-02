package com.example.straight_habits.activity.routine

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.straight_habits.R
import com.example.straight_habits.adapters.days.EditRoutineDaysAdapter
import com.example.straight_habits.facade.ManageDaysFacade
import com.example.straight_habits.interfaces.SelectDayInterface
import com.example.straight_habits.models.DayModel

class EditRoutineActivity : AppCompatActivity(), SelectDayInterface {
    //Days
    private lateinit var rvDays: RecyclerView
    private lateinit var daysAdapter: EditRoutineDaysAdapter
    private lateinit var daysList: MutableList<DayModel>


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_routine)

        //Remove the Action Bar
        supportActionBar?.hide()


        //Set Days
        setDays()
    }




    //Show Days
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setDays(){
        //Days List
        daysList = ArrayList()
        daysList.add(DayModel("Monday", false, "M"))
        daysList.add(DayModel("Tuesday", false, "T"))
        daysList.add(DayModel("Wednesday", false, "W"))
        daysList.add(DayModel("Thursday", false, "T"))
        daysList.add(DayModel("Friday", false, "F"))
        daysList.add(DayModel("Saturday", false, "S"))
        daysList.add(DayModel("Sunday", false, "S"))
        //Set Selected
        var position = 0
        for(day in daysList){
            if(day.name == ManageDaysFacade.getCurrentDay()){
                day.selected = true
                break
            }
            else
                position++
        }


        //Adapter
        daysAdapter = EditRoutineDaysAdapter(daysList, this)


        //Recycler View
        rvDays = findViewById(R.id.rv_days)
        rvDays.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        rvDays.adapter = daysAdapter
        //Scroll to Selected Position
        rvDays.scrollToPosition(position)
    }





    //Interface Methods-----------------------------------------------------------------------------
    //Days Interface
    override fun selectDay(position: Int) {
        //Deselect all
        for(day in daysList)
            day.selected = false
        //Select the Clicked One
        daysList[position].selected = true


        //Update the Adapter
        daysAdapter.notifyDataSetChanged()
    }
}