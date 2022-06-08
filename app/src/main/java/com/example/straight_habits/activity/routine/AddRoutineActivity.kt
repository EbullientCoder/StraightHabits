package com.example.straight_habits.activity.routine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.straight_habits.R
import com.example.straight_habits.adapters.days.AddRoutineDaysAdapter
import com.example.straight_habits.adapters.categories.AddRoutineCategoriesAdapter
import com.example.straight_habits.beans.RoutineBean
import com.example.straight_habits.controller.application.ManageRoutine
import com.example.straight_habits.controller.graphic.AddRoutineGraphicController
import com.example.straight_habits.database.RoomDB
import com.example.straight_habits.interfaces.SelectDayInterface
import com.example.straight_habits.interfaces.categories.SelectCategoryInterface
import com.example.straight_habits.models.CategoryModel
import com.example.straight_habits.models.DayModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddRoutineActivity : AppCompatActivity(), SelectCategoryInterface, SelectDayInterface {
    //Button
    private lateinit var btnClose: ImageView
    private lateinit var btnCreate: FloatingActionButton
    //Categories
    private lateinit var rvCategories: RecyclerView
    private lateinit var categoriesAdapter: AddRoutineCategoriesAdapter
    private lateinit var categoriesList: MutableList<CategoryModel>
    //Days
    private lateinit var rvDays: RecyclerView
    private lateinit var addRoutineDaysAdapter: AddRoutineDaysAdapter
    private lateinit var daysList: MutableList<DayModel>

    //Controller
    private lateinit var graphicController: AddRoutineGraphicController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_routine)

        //Controller
        graphicController = AddRoutineGraphicController(this)


        //Set Button
        setButtons()

        //Categories
        //Using Coroutines to Manage the Room DB
        lifecycleScope.launch(Dispatchers.IO){
            //Get Categories List
            getCategoriesList()

            //Set the Recycler View
            setCategoriesRecyclerView()
        }

        //Days
        //Get Days List
        getDaysList()
        //Set Recycler View
        setDaysRecyclerView()
    }




    //Set Buttons
    private fun setButtons(){
        //Close Fragment
        btnClose = findViewById(R.id.btn_close_add_habit)
        btnClose.setOnClickListener {
            //Calling the "Main Habit" Activity
            val intent = Intent(this, ShowRoutineActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        //Create the new Habit and go back to Main Activity
        btnCreate = findViewById(R.id.btn_create_habit)
        btnCreate.setOnClickListener{
            //Application Model
            val manageHabit = ManageRoutine()
            //Beans
            var routineBeans: MutableList<RoutineBean> = ArrayList()

            //Category Loop
            for(category in categoriesList){
                //If the Category is selected create the bean
                if(category.getSelected()){
                    val bean = graphicController.getHabit(category)
                    routineBeans.add(bean)
                }
            }

            GlobalScope.launch {
                //Day Loop
                for(day in daysList){
                    //If the Day is selected create the Model
                    for(habit in routineBeans)
                        if(day.selected)
                            manageHabit.addRoutine(habit, day.id, applicationContext)
                }
            }


            //Calling the "Main Habit" Activity
            val intent = Intent(this, ShowRoutineActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }



    //Categories
    //Get Categories
    private suspend fun getCategoriesList(){
        //Database Instance
        val DB = RoomDB.getInstance(applicationContext).categoryDAO()

        //Habits List
        categoriesList = DB.readAll()
    }

    //Set Recycler View
    private fun setCategoriesRecyclerView(){
        //Recycler View
        rvCategories = findViewById(R.id.rv_add_habit_categories)

        //Adapter
        categoriesAdapter = AddRoutineCategoriesAdapter(categoriesList, this)

        //Recycler View
        rvCategories
            .layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        rvCategories.adapter = categoriesAdapter
    }


    //Days
    //Create Days List
    private fun getDaysList(){
        daysList = ArrayList()
        daysList.add(DayModel("M", true, "Monday"))
        daysList.add(DayModel("T", false, "Tuesday"))
        daysList.add(DayModel("W", false,"Wednesday"))
        daysList.add(DayModel("T", false,"Thursday"))
        daysList.add(DayModel("F", false, "Friday"))
        daysList.add(DayModel("S", false, "Saturday"))
        daysList.add(DayModel("S", false, "Sunday"))
    }

    //Set Recycler View
    private fun setDaysRecyclerView(){
        //Recycler View
        rvDays = findViewById(R.id.rv_add_habit_days)

        //Adapter
        addRoutineDaysAdapter = AddRoutineDaysAdapter(daysList, this)

        //Recycler View
        rvDays
            .layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        rvDays.adapter = addRoutineDaysAdapter
    }





    //Interface Methods-----------------------------------------------------------------------------
    //Category Interface
    override fun selectCategory(position: Int) {
        //Check or Uncheck Category
        if(categoriesList[position].getSelected())
            categoriesList[position].setSelected(false)
        else
            categoriesList[position].setSelected(true)

        //Upgrade Adapter
        categoriesAdapter.notifyDataSetChanged()
    }



    //Day Interface
    override fun selectDay(position: Int) {
        //Check or Uncheck Day
        if(daysList[position].selected)
            daysList[position].selected = false
        else
            daysList[position].selected = true

        //Upgrade Adapter
        addRoutineDaysAdapter.notifyDataSetChanged()
    }
}