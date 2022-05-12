package com.example.straight_habits.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.straight_habits.R
import com.example.straight_habits.controller.application.ManageHabits
import com.example.straight_habits.controller.graphic.AddHabitGraphicController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddHabitActivity : AppCompatActivity() {
    //Buttons
    private lateinit var btnHabitDone: FloatingActionButton

    //Graph Controller
    private lateinit var graphicController: AddHabitGraphicController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_habit)

        //Graphic Controller
        graphicController = AddHabitGraphicController(this)

        //Set Buttons
        setButtons()
    }



    //Set the Buttons Function
    private fun setButtons(){
        btnHabitDone = findViewById(R.id.btn_done)
        btnHabitDone.setOnClickListener{
            //Application Controller
            val manageHabit = ManageHabits()
            val newHabit = graphicController.getHabit()

            //Coroutine
            lifecycleScope.launch(Dispatchers.IO){
                //Add the Habit to the DB
                manageHabit.addHabit(newHabit, graphicController.getDay(), applicationContext)
            }

            //Add the Habit Bean to the List
            val intent = Intent(this, MainActivity::class.java).apply {
                //Pass the new habit to the Main View
                putExtra("New Habit", newHabit)
            }
            //Go back to Main
            startActivity(intent)
        }
    }
}