package com.example.straight_habits.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.straight_habits.R
import com.example.straight_habits.adapters.categories.AddHabitCategoriesAdapter
import com.example.straight_habits.adapters.categories.CategoriesAdapter
import com.example.straight_habits.controller.graphic.AddHabitGraphicController
import com.example.straight_habits.database.RoomDB
import com.example.straight_habits.interfaces.SelectCategoryInterface
import com.example.straight_habits.models.CategoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddHabitActivity : AppCompatActivity(), SelectCategoryInterface {
    //Categories
    private lateinit var rvCategories: RecyclerView
    private lateinit var categoriesAdapter: AddHabitCategoriesAdapter
    private lateinit var categoriesList: MutableList<CategoryModel>
    //Days
    private lateinit var rvDays: RecyclerView
    //private lateinit var daysAdapter: DaysAdapter
    private lateinit var daysList: MutableList<String>

    //Controller
    private lateinit var graphicController: AddHabitGraphicController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_habit)

        //Controller
        graphicController = AddHabitGraphicController(this)

        //Categories
        //Using Coroutines to Manage the Room DB
        lifecycleScope.launch(Dispatchers.IO){
            //Get Categories List
            getCategoriesList()

            //Set the Recycler View
            setCategoriesRecyclerView()
        }

        //Days
        getDaysList()
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
        categoriesAdapter = AddHabitCategoriesAdapter(categoriesList, this)

        //Recycler View
        rvCategories
            .layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        rvCategories.adapter = categoriesAdapter
    }


    //Days
    //Create Days List
    private fun getDaysList(){
        daysList = ArrayList()
        daysList.add("M")
        daysList.add("T")
        daysList.add("W")
        daysList.add("T")
        daysList.add("F")
        daysList.add("S")
        daysList.add("S")
    }

    //Set Recycler View
    private fun setDaysRecyclerView(){
        //Recycler View
        rvDays = findViewById(R.id.rv_add_habit_days)

        //Adapter
        //daysAdapter = AddHabitCategoriesAdapter(categoriesList, this)

        //Recycler View
        rvDays
            .layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        //rvDays.adapter = daysAdapter
    }





    //Interface Methods-----------------------------------------------------------------------------
    override fun selectCategory(position: Int) {
        //Check or Uncheck Category
        if(categoriesList[position].getSelected())
            categoriesList[position].setSelected(false)
        else
            categoriesList[position].setSelected(true)

        //Upgrade Adapter
        categoriesAdapter.notifyDataSetChanged()
    }
}