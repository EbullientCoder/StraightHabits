package com.example.straight_habits.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.example.straight_habits.R
import com.example.straight_habits.controller.application.ManageCategories
import com.example.straight_habits.controller.application.ManageHabits
import com.example.straight_habits.controller.graphic.MainGraphicController
import com.example.straight_habits.facade.ManageCategoriesFacade
import com.example.straight_habits.facade.ManageDaysFacade
import com.example.straight_habits.facade.ManageHabitsFacade
import com.example.straight_habits.fragments.categories.EditCategoriesFragment
import com.example.straight_habits.fragments.habits.EditHabitsFragment
import com.example.straight_habits.fragments.categories.ShowCategoriesFragment
import com.example.straight_habits.fragments.habits.ShowHabitsFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File


class MainActivity : AppCompatActivity() {
    //Buttons
    private lateinit var btnMenu: ImageView
    private lateinit var btnDone: ImageView
    private lateinit var btnAddHabit: FloatingActionButton
    private lateinit var btnDelete: ImageView
    private lateinit var txtDelete: TextView
    private lateinit var btnEdit: ImageView
    private lateinit var txtEdit: TextView

    //Fragment
    //Habits
    private lateinit var showHabitsFragment: ShowHabitsFragment
    private lateinit var editHabitsFragment: EditHabitsFragment
    //Categories
    private lateinit var showCategoriesFragment: ShowCategoriesFragment
    private lateinit var editCategoriesFragment: EditCategoriesFragment

    //Graph Controller
    private lateinit var graphicController: MainGraphicController




    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Using Shared Preferences to check if the day is changed from the last access
        resetList()

        //Set the Graphic Elements
        graphicController = MainGraphicController(this)

        //Set Fragments
        setCategoriesFragment(false)
        showCategoriesFragment.selectFragment(false)


        //Buttons
        setButtons()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRestart() {
        super.onRestart()

        //On Restart the Graphic Controller will be called so it can update the Habits List
        //Using Coroutines to Manage the Room DB
        /*lifecycleScope.launch(Dispatchers.IO){
            //Get HabitBeans List
            //graphicController.getHabitsList()

            //Set the Recycler View
            //graphicController.setHabitsRecyclerView()
        }*/
    }




    //Set Shared Preferences
    @RequiresApi(Build.VERSION_CODES.O)
    private fun resetList(){
        val f = File("/data/data/your_application_package/shared_prefs/MyPreferences.xml")

        //If the file doesn't exist it will be created and will be saved the current day
        if (!f.exists()){
            val sp = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sp.edit()
            editor.putString("CURRENT_DAY", ManageDaysFacade.getCurrentDay())
            editor.commit()
        }
        //Else will be checked if the day stored in the file is the current day.
        //If it's not, the prev day will reset
        else {
            val sp = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
            if(!sp.getString("CURRENT_DAY", "").equals(ManageDaysFacade.getCurrentDay())){
                //Updating the stored Day Value
                val editor: SharedPreferences.Editor = sp.edit()
                editor.clear()
                editor.putString("CURRENT_DAY", ManageDaysFacade.getCurrentDay())
                editor.commit()

                //Resetting the Habits List
                lifecycleScope.launch(Dispatchers.IO){
                    ManageDaysFacade.resetPrevDayHabits(ManageDaysFacade.getCurrentDay(), applicationContext)
                }
            }
        }
    }




    //Set the Buttons Functions
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setButtons(){
        var open = false

        //Menu Button
        btnMenu =  findViewById(R.id.btn_menu)
        btnMenu.setOnClickListener{
            if(!open){
                txtDelete.visibility = View.VISIBLE
                btnDelete.visibility = View.VISIBLE
                txtEdit.visibility = View.VISIBLE
                btnEdit.visibility = View.VISIBLE

                open = true
            }
            else{
                txtDelete.visibility = View.GONE
                btnDelete.visibility = View.GONE
                txtEdit.visibility = View.GONE
                btnEdit.visibility = View.GONE

                open = false
            }
        }

        //Button Delete
        txtDelete = findViewById(R.id.txt_delete)
        txtDelete.visibility = View.GONE
        btnDelete = findViewById(R.id.btn_delete)
        btnDelete.visibility = View.GONE
        btnDelete.setOnClickListener{
            //Reset Habits list
            testDeleteAndRebuildHabits()
            //Reset Categories list
            testDeleteAndRebuildCategories()

            //Recreate the Show Fragments
            //Show Habits Fragment
            showHabitsFragment = ShowHabitsFragment()
            supportFragmentManager
                .beginTransaction().replace(R.id.habits_fragment_container, showHabitsFragment).commit()
            //Show Categories Fragment
            showCategoriesFragment = ShowCategoriesFragment()
            supportFragmentManager
                .beginTransaction().replace(R.id.categories_fragment_container, showCategoriesFragment).commit()


            btnMenu.performClick()
        }

        //Button Edit
        txtEdit = findViewById(R.id.txt_edit)
        txtEdit.visibility = View.GONE
        btnEdit = findViewById(R.id.btn_edit)
        btnEdit.visibility = View.GONE
        btnEdit.setOnClickListener{
            //Edit Fragment
            setCategoriesFragment(true)
            showCategoriesFragment.selectFragment(true)

            //Retire the Icons
            btnMenu.performClick()

            //Hide the Menu Button
            btnMenu.visibility = View.GONE

            //Show the Done Button
            btnDone.visibility = View.VISIBLE
        }

        //Button Done
        btnDone = findViewById(R.id.btn_edit_done)
        btnDone.visibility = View.GONE
        btnDone.setOnClickListener{
            //Show Fragment
            setCategoriesFragment(false)
            showCategoriesFragment.selectFragment(false)

            //Hide Button Done
            btnDone.visibility = View.GONE

            //Show Button Menu
            btnMenu.visibility = View.VISIBLE
        }



        //Floating Button
        //Add Habit Button
        btnAddHabit = findViewById(R.id.btn_add_habit)
        btnAddHabit.setOnClickListener{
            //Calling the "Add Habit" Activity
            val intent = Intent(this, AddHabitActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }



    //Update the Fragment showed list
    fun setHabitsFragment(category: String, edit: Boolean){
        val bundle = Bundle()
        bundle.putString("Category", category)

        //Set Fragment
        //Check if create Show Habits Fragment or Edit Habits Fragment
        if(edit){
           //Edit Fragment
            editHabitsFragment = EditHabitsFragment()
            editHabitsFragment.arguments = bundle

            supportFragmentManager
                .beginTransaction().replace(R.id.habits_fragment_container, editHabitsFragment).commit()
        }
        else{
            //Show Fragment
            showHabitsFragment = ShowHabitsFragment()
            showHabitsFragment.arguments = bundle

            supportFragmentManager
                .beginTransaction().replace(R.id.habits_fragment_container, showHabitsFragment).commit()
        }
    }

    //Set Categories Fragment
    private fun setCategoriesFragment(edit: Boolean){
        //Set Fragment
        //Check if create Show Habits Fragment or Edit Habits Fragment
        if(edit){
            //Edit Fragment
            editCategoriesFragment = EditCategoriesFragment()

            supportFragmentManager
                .beginTransaction().replace(R.id.categories_fragment_container, editCategoriesFragment).commit()
        }
        else{
            //Show Fragment
            showCategoriesFragment = ShowCategoriesFragment()

            supportFragmentManager
                .beginTransaction().replace(R.id.categories_fragment_container, showCategoriesFragment).commit()
        }
    }





    //Test------------------------------------------------------------------------------------------
    //Delete and ReBuild the habits list
    @RequiresApi(Build.VERSION_CODES.O)
    private fun testDeleteAndRebuildHabits(){
        //Reset the Current Habits list
        //Delete the Old List from the DB
        runBlocking{
            //Application Controller
            val manageHabits = ManageHabits()

            manageHabits.deleteAllHabitsFromDay(this@MainActivity)
            //Add a new One
            manageHabits.addHabitsList(ManageHabitsFacade.createHabitsList(), this@MainActivity)
        }

        //Notify the User
        Toast.makeText(this, "THE HABITS LIST HAS BEEN RESTORED", Toast.LENGTH_SHORT).show()
    }

    private fun testDeleteAndRebuildCategories(){
        //Reset the Current Categories list
        //Delete the Old List from the DB
        runBlocking{
            //Application Controller
            val manageCategories = ManageCategories()

            manageCategories.deleteAllCategories(this@MainActivity)
            //Add a new One
            manageCategories.addCategoriesList(ManageCategoriesFacade.createCategoriesList(), this@MainActivity)
        }

        //Notify the User
        Toast.makeText(this, "THE CATEGORIES LIST HAS BEEN RESTORED", Toast.LENGTH_SHORT).show()
    }
}