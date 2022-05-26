package com.example.straight_habits.controller.graphic

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.widget.EditText
import android.widget.TimePicker
import com.example.straight_habits.R
import com.example.straight_habits.activity.AddHabitActivity
import com.example.straight_habits.beans.HabitBean
import com.example.straight_habits.models.CategoryModel
import java.util.*


class AddHabitGraphicController(view: AddHabitActivity): TimePickerDialog.OnTimeSetListener {
    private val addHabitInstance = view
    //Text
    private val txtHabitName: EditText
    private val txtHabitInfo: EditText
    private val txtHabitStart: EditText
    private val txtHabitEnd: EditText
    private var hour: Int = 0
    private var savedHour = 0
    private var minute: Int = 0
    private var savedMinute = 0
    private var style = AlertDialog.THEME_HOLO_DARK



    //First block of code to be executed
    init {
        //Remove the Action Bar
        addHabitInstance.supportActionBar?.hide()

        //Text
        txtHabitName = view.findViewById(R.id.txt_add_habit_name)
        txtHabitInfo = view.findViewById(R.id.txt_add_habit_info)
        txtHabitStart = view.findViewById(R.id.txt_add_habit_start)
        txtHabitEnd = view.findViewById(R.id.txt_add_habit_end)
    }


    //Set the Click Listener
    private fun setDate(){
        val cal = Calendar.getInstance()
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)


        //When Clicked open the Time Picker
        txtHabitStart.setOnClickListener{

        }

        //When Clicked open the Time Picker
        txtHabitEnd.setOnClickListener{

        }
    }



    //Return Habit Bean
    fun getHabit(category: CategoryModel): HabitBean{
        var name = "NULL"
        var info = "NULL"
        var start = "NULL"
        var end = "NULL"

        //Name
        if (txtHabitName.editableText.toString() != null)
            name = txtHabitName.editableText.toString()
        //info
        if (txtHabitInfo.editableText.toString() != null)
            info = txtHabitInfo.editableText.toString()
        //Start
        if (txtHabitStart.editableText.toString() != null)
            start = txtHabitStart.editableText.toString()
        //End
        if (txtHabitEnd.editableText.toString() != null)
            end = txtHabitEnd.editableText.toString()

        //Return the Habit Bean
        return HabitBean(0, name, info, category.getName(), start, end, false, false)
    }





    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}