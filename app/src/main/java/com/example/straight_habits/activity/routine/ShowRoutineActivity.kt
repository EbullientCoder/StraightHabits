package com.example.straight_habits.activity.routine

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.example.straight_habits.R
import com.example.straight_habits.beans.RoutineBean
import com.example.straight_habits.controller.application.ManageCategories
import com.example.straight_habits.controller.application.ManageRoutine
import com.example.straight_habits.controller.graphic.ShowRoutineGraphicController
import com.example.straight_habits.facade.ManageCategoriesFacade
import com.example.straight_habits.facade.ManageDaysFacade
import com.example.straight_habits.facade.ManageRoutineFacade
import com.example.straight_habits.fragments.categories.ShowCategoriesFragment
import com.example.straight_habits.fragments.routine.ShowRoutineFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File


class ShowRoutineActivity : AppCompatActivity() {
    //Buttons
    private lateinit var btnMenu: ImageView
    private lateinit var btnDone: ImageView
    private lateinit var btnAddHabit: FloatingActionButton
    private lateinit var btnDelete: ImageView
    private lateinit var txtDelete: TextView
    private lateinit var btnEdit: ImageView
    private lateinit var txtEdit: TextView

    //Fragment
    //Routine
    private lateinit var showRoutineFragment: ShowRoutineFragment
    //Categories
    private lateinit var showCategoriesFragment: ShowCategoriesFragment

    //Graph Controller
    private lateinit var graphicController: ShowRoutineGraphicController




    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_routine)

        //Using Shared Preferences to check if the day is changed from the last access
        resetList()

        //Set the Graphic Elements
        graphicController = ShowRoutineGraphicController(this)

        //Set Fragments
        setCategoriesFragment(false)
        showCategoriesFragment.selectFragment(false)


        //Buttons
        setButtons()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRestart() {
        super.onRestart()

        //On Restart the fragments will be recreated
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
            testDeleteAndRebuildRoutine()
            //Reset Categories list
            testDeleteAndRebuildCategories()

            //Recreate the Show Fragments
            setCategoriesFragment(false)
            showCategoriesFragment.selectFragment(false)

            btnMenu.performClick()
        }

        //Button Edit
        txtEdit = findViewById(R.id.txt_edit)
        txtEdit.visibility = View.GONE
        btnEdit = findViewById(R.id.btn_edit)
        btnEdit.visibility = View.GONE
        btnEdit.setOnClickListener{
            //Edit Fragment
            /*setCategoriesFragment(true)
            showCategoriesFragment.selectFragment(true)

            //Retire the Icons
            btnMenu.performClick()

            //Hide the Menu Button
            btnMenu.visibility = View.GONE

            //Show the Done Button
            btnDone.visibility = View.VISIBLE*/


            //Calling the "Edit Routine" Activity
            val intent = Intent(this, EditRoutineActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
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
            val intent = Intent(this, AddRoutineActivity::class.java)
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
            //editRoutineFragment = EditRoutineFragment()
            //editRoutineFragment.arguments = bundle

            //supportFragmentManager
            //    .beginTransaction().replace(R.id.routine_fragment_container, editRoutineFragment).commit()
        }
        else{
            //Show Fragment
            showRoutineFragment = ShowRoutineFragment()
            showRoutineFragment.arguments = bundle

            supportFragmentManager
                .beginTransaction().replace(R.id.routine_fragment_container, showRoutineFragment).commit()
        }
    }

    //Set Categories Fragment
    private fun setCategoriesFragment(edit: Boolean){
        //Set Fragment
        //Check if create Show Habits Fragment or Edit Habits Fragment
        if(edit){
            //Edit Fragment
            //editCategoriesFragment = EditCategoriesFragment()

            //supportFragmentManager
            //    .beginTransaction().replace(R.id.categories_fragment_container, editCategoriesFragment).commit()
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
    private fun testDeleteAndRebuildRoutine(){
        //Get Diet Routine
        var dietList = ManageRoutineFacade.createRoutineDietList()

        //Shared------------------------------------------------------------------------------------
        var shared: MutableList<RoutineBean> = ArrayList()
        //Breakfast
        shared.add(dietList[0])
        //Mid Morning Snack
        shared.add(dietList[1])
        //Mid Evening Snack
        shared.add(dietList[3])

        //Monday & Thursday-------------------------------------------------------------------------
        var mon_thu: MutableList<RoutineBean> = ArrayList()
        //Launch
        mon_thu.add(dietList[2])
        //Dinner
        mon_thu.add(dietList[4])

        //Tuesday & Friday--------------------------------------------------------------------------
        var tue_fri: MutableList<RoutineBean> = ArrayList()
        //Launch
        tue_fri.add(dietList[5])
        //Dinner
        tue_fri.add(dietList[6])

        //Wednesday & Saturday--------------------------------------------------------------------------
        var wed_sat: MutableList<RoutineBean> = ArrayList()
        //Launch
        wed_sat.add(dietList[7])
        //Dinner
        wed_sat.add(dietList[8])

        //Sunday------------------------------------------------------------------------------------
        var sun: MutableList<RoutineBean> = ArrayList()
        //Launch
        sun.add(dietList[9])
        //Dinner
        sun.add(dietList[10])



        //Reset the Current Habits list
        //Delete the Old List from the DB
        runBlocking{
            //Application Controller
            val manageRoutine = ManageRoutine()

            //Delete the Old List
            //manageRoutine.deleteAllRoutineFromDay(this@ShowRoutineActivity)
            manageRoutine.deleteAllRoutine(this@ShowRoutineActivity)
            //Add a new One
            //manageRoutine.addRoutineListCurrentDay(ManageRoutineFacade.createRoutineList(), this@ShowRoutineActivity)
            manageRoutine.addRoutineListAllDay(ManageRoutineFacade.createRoutineList(), this@ShowRoutineActivity)
            manageRoutine.addRoutineListAllDay(shared, this@ShowRoutineActivity)

            //Monday
            manageRoutine.addRoutineListSelectedDay(mon_thu, "Monday", this@ShowRoutineActivity)
            //Tuesday
            manageRoutine.addRoutineListSelectedDay(tue_fri, "Tuesday", this@ShowRoutineActivity)
            //Wednesday
            manageRoutine.addRoutineListSelectedDay(wed_sat, "Wednesday", this@ShowRoutineActivity)
            //Thursday
            manageRoutine.addRoutineListSelectedDay(mon_thu, "Thursday", this@ShowRoutineActivity)
            //Friday
            manageRoutine.addRoutineListSelectedDay(tue_fri, "Friday", this@ShowRoutineActivity)
            //Saturday
            manageRoutine.addRoutineListSelectedDay(wed_sat, "Saturday", this@ShowRoutineActivity)
            //Sunday
            manageRoutine.addRoutineListSelectedDay(sun, "Sunday", this@ShowRoutineActivity)
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

            manageCategories.deleteAllCategories(this@ShowRoutineActivity)
            //Add a new One
            manageCategories.addCategoriesList(ManageCategoriesFacade.createCategoriesList(), this@ShowRoutineActivity)
        }

        //Notify the User
        Toast.makeText(this, "THE CATEGORIES LIST HAS BEEN RESTORED", Toast.LENGTH_SHORT).show()
    }
}