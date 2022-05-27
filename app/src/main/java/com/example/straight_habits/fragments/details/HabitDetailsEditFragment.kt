package com.example.straight_habits.fragments.details

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.straight_habits.R
import com.example.straight_habits.beans.HabitBean
import com.example.straight_habits.controller.application.ManageHabits
import com.example.straight_habits.facade.ManageDaysFacade
import com.example.straight_habits.facade.ManageHabitsFacade
import com.example.straight_habits.interfaces.UpdateEditHabitsListInterface
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.runBlocking


class HabitDetailsEditFragment(val updateEditHabitsListInterface: UpdateEditHabitsListInterface) : DialogFragment() {
    //Button
    private lateinit var btnBack: ImageView
    private lateinit var btnDone: FloatingActionButton
    //Text
    private lateinit var txtName: EditText
    private lateinit var txtInfo: EditText
    private lateinit var txtCategory: EditText
    private lateinit var txtStart: EditText
    private lateinit var txtEnd: EditText

    //Habit
    private lateinit var habit: HabitBean


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Set transparent background and no title
        if (dialog != null && dialog!!.window != null) {
            dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dialog!!.window?.requestFeature(Window.FEATURE_NO_TITLE);
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_habit_details_edit, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Back Button
        btnBack = view.findViewById(R.id.btn_habit_details_edit_back)
        btnBack.setOnClickListener {
            dismiss()
        }

        //Done Button
        btnDone = view.findViewById(R.id.btn_habit_details_edit_done)
        btnDone.setOnClickListener{
            //If the Habit has been updated than print it and close the fragment
            if(editHabit(habit)){
                Toast.makeText(requireContext(), "Habit Updated!", Toast.LENGTH_SHORT).show()

                updateEditHabitsListInterface.updateList()
            }

            dismiss()
        }



        //Habit's Details
        txtName = view.findViewById(R.id.txt_habit_details_edit_name)
        txtInfo = view.findViewById(R.id.txt_habit_details_edit_information)
        txtCategory = view.findViewById(R.id.txt_habit_details_edit_category)
        txtStart = view.findViewById(R.id.txt_habit_details_edit_start)
        txtEnd = view.findViewById(R.id.txt_habit_details_edit_end)

        //Get Bundle
        val bundle = arguments

        //Get Habit
        habit = bundle!!.getSerializable("Edit Habit Details") as HabitBean

        setText(habit)
    }



    private fun setText(habit: HabitBean){
        txtName.hint = habit.getName()
        txtInfo.hint = habit.getInformation()
        txtCategory.hint = habit.getCategory()
        txtStart.hint = habit.getStartHour()
        txtEnd.hint = habit.getEndHour()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun editHabit(habit: HabitBean): Boolean{
        //Application Controller
        val manageHabits = ManageHabits()

        //Get Text
        var name: String
        var info: String
        var category: String
        var start: String
        var end: String

        //Name
        if (txtName.editableText.toString() != ""){
            name = txtName.editableText.toString()
            habit.setName(name)
        }
        //Information
        if (txtInfo.editableText.toString() != ""){
            info = txtInfo.editableText.toString()
            habit.setInformation(info)
        }
        //Category
        if (txtCategory.editableText.toString() != ""){
            category = txtCategory.editableText.toString()
            habit.setCategory(category)
        }
        //Start
        if (txtStart.editableText.toString() != ""){
            start = txtStart.editableText.toString()
            habit.setStart(start)
        }
        //End
        if (txtEnd.editableText.toString() != ""){
            end = txtEnd.editableText.toString()
            habit.setEnd(end)
        }


        //Update the DB
        runBlocking {
            manageHabits
                .editHabit(
                    ManageHabitsFacade.beanToModel(habit, ManageDaysFacade.getCurrentDay()),
                    requireContext())
        }

        return true
    }
}