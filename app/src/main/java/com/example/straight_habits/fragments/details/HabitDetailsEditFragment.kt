package com.example.straight_habits.fragments.details

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.straight_habits.R
import com.example.straight_habits.beans.HabitBean


class HabitDetailsEditFragment : DialogFragment() {
    //Button
    private lateinit var btnBack: ImageView
    //Text
    private lateinit var txtName: EditText
    private lateinit var txtInfo: EditText
    private lateinit var txtCategory: EditText
    private lateinit var txtStart: EditText
    private lateinit var txtEnd: EditText


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Back Button
        btnBack = view.findViewById(R.id.btn_habit_details_edit_back)
        btnBack.setOnClickListener {
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
        val habit: HabitBean = bundle!!.getSerializable("Edit Habit Details") as HabitBean
        setText(habit)
    }



    private fun setText(habit: HabitBean){
        txtName.hint = habit.getName()
        txtInfo.hint = habit.getInformation()
        txtCategory.hint = habit.getCategory()
        txtStart.hint = habit.getStartHour()
        txtEnd.hint = habit.getEndHour()
    }
}