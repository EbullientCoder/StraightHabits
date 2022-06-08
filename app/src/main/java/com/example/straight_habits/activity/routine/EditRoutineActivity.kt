package com.example.straight_habits.activity.routine

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.straight_habits.R
import com.example.straight_habits.adapters.categories.EditCategoriesAdapter
import com.example.straight_habits.adapters.days.EditRoutineDaysAdapter
import com.example.straight_habits.adapters.routine.EditRoutineAdapter
import com.example.straight_habits.beans.RoutineBean
import com.example.straight_habits.controller.application.ManageRoutine
import com.example.straight_habits.database.RoomDB
import com.example.straight_habits.facade.ManageDaysFacade
import com.example.straight_habits.facade.ManageRoutineFacade
import com.example.straight_habits.fragments.details.RoutineDetailsEditFragment
import com.example.straight_habits.interfaces.SelectDayInterface
import com.example.straight_habits.interfaces.routine.UpdateEditedRoutineInterface
import com.example.straight_habits.interfaces.categories.SelectCategoryInterface
import com.example.straight_habits.interfaces.routine.EditRoutineInterface
import com.example.straight_habits.models.CategoryModel
import com.example.straight_habits.models.DayModel
import com.example.straight_habits.models.RoutineModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class EditRoutineActivity : AppCompatActivity(),
    SelectDayInterface,
    SelectCategoryInterface,
    EditRoutineInterface,
    UpdateEditedRoutineInterface {
    //Days
    private lateinit var rvDays: RecyclerView
    private lateinit var daysAdapter: EditRoutineDaysAdapter
    private lateinit var daysList: MutableList<DayModel>
    //Categories
    private lateinit var rvCategories: RecyclerView
    private lateinit var categoriesAdapter: EditCategoriesAdapter
    private lateinit var categoriesList: MutableList<CategoryModel>
    //Routine
    private lateinit var rvRoutine: RecyclerView
    private lateinit var routineAdapter: EditRoutineAdapter
    private lateinit var routineList: MutableList<RoutineBean>

    //Selected
    private lateinit var selectedCategory: CategoryModel
    private lateinit var selectedDay: DayModel

    //Button
    private lateinit var btnDone: FloatingActionButton



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_routine)

        //Remove the Action Bar
        supportActionBar?.hide()


        //Set Buttons
        setButtons()

        //Set Days
        setDays()

        //Set Categories
        setCategories()

        //Set Routine
        setRoutine()
    }




    //Set Buttons
    private fun setButtons(){
        btnDone = findViewById(R.id.btn_done)
        btnDone.setOnClickListener{
            //Calling the "Main Habit" Activity
            val intent = Intent(this, ShowRoutineActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
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
                selectedDay = day

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


    //Set Categories
    private fun setCategories(){
        //Using Coroutines to Manage the Room DB
        runBlocking{
            //Database Instance
            val DB = RoomDB.getInstance(applicationContext).categoryDAO()

            //Categories List
            categoriesList = DB.readAll()
            selectedCategory = categoriesList[0]


            //Adapter
            categoriesAdapter = EditCategoriesAdapter(categoriesList, this@EditRoutineActivity)


            //Recycler View
            rvCategories = findViewById(R.id.rv_categories)
            rvCategories.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            rvCategories.adapter = categoriesAdapter
        }
    }


    //Set Routine
    private fun setRoutine(){
        //Using Coroutines to Manage the Room DB
        lifecycleScope.launch(Dispatchers.IO){
            //Database Instance
            val DB = RoomDB.getInstance(applicationContext).routineDAO()

            //Routine List
            val routine = DB.readAllCategory(selectedDay.name, selectedCategory.getName())
            //Get the beans
            routineList = ArrayList()
            for(i in 0 until routine.size){
                //Create the Bean
                //Add the Bean to the List
                routineList.add(
                    RoutineBean(
                        routine[i].getID(),
                        routine[i].getName(),
                        routine[i].getInformation(),
                        routine[i].getCategory(),
                        routine[i].getStartHour(),
                        routine[i].getEndHour(),
                        routine[i].getSelected(),
                        routine[i].getDone()
                    )
                )
            }
            //Order the Routine List
            ManageRoutineFacade.orderRoutine(routineList)


            //Adapter
            routineAdapter = EditRoutineAdapter(routineList, this@EditRoutineActivity)


            //Recycler View
            rvRoutine = findViewById(R.id.rv_routine)
            rvRoutine.layoutManager = LinearLayoutManager(applicationContext)
            rvRoutine.adapter = routineAdapter
        }
    }


    //Update the Routine List
    private suspend fun updateRoutine(routineList: MutableList<RoutineBean>){
        //Empty the List
        routineList.removeAll(routineList)

        //Database Instance
        val DB = RoomDB.getInstance(applicationContext).routineDAO()

        //Routine List
        val routine = DB.readAllCategory(selectedDay.name, selectedCategory.getName())
        //Get Beans
        for(i in 0 until routine.size){
            //Create the Bean
            //Add the Bean to the List
            routineList.add(
                RoutineBean(
                    routine[i].getID(),
                    routine[i].getName(),
                    routine[i].getInformation(),
                    routine[i].getCategory(),
                    routine[i].getStartHour(),
                    routine[i].getEndHour(),
                    routine[i].getSelected(),
                    routine[i].getDone()
                )
            )
        }
    }






    //Interface Methods-----------------------------------------------------------------------------
    //Days Interface--------------------------------------------------------------------------------
    override fun selectDay(position: Int) {
        //Deselect all
        for(day in daysList)
            day.selected = false
        //Select the Clicked One
        daysList[position].selected = true
        selectedDay = daysList[position]


        //Update the Adapter
        daysAdapter.notifyDataSetChanged()

        //Update the Showed Routine
        runBlocking {
            updateRoutine(routineList)
        }
        routineAdapter.notifyDataSetChanged()
    }


    //Categories Interface--------------------------------------------------------------------------
    override fun selectCategory(position: Int) {
        //Deselect All
        for(category in categoriesList)
            category.setSelected(false)
        //Select the Clicked One
        categoriesList[position].setSelected(true)
        selectedCategory = categoriesList[position]

        //Notify the Adapter
        categoriesAdapter.notifyDataSetChanged()

        //Update the Showed Routine
        runBlocking {
            updateRoutine(routineList)
        }
        routineAdapter.notifyDataSetChanged()
    }


    //Routine Interface-----------------------------------------------------------------------------
    override fun deleteHabit(position: Int) {
        //Application Controller
        val manageRoutine = ManageRoutine()

        //Coroutines
        runBlocking {
            //Delete Routine
            val routineModel =
                ManageRoutineFacade.beanToModel(routineList[position], selectedDay.name)
            manageRoutine.deleteRoutine(routineModel, applicationContext)

            //Check if the Routine was Selected. If so, select the next one that is not done
            if (routineList[position].getSelected()) {
                val i = ManageRoutineFacade.getNotDone(routineList, position + 1)

                runOnUiThread {
                    Toast.makeText(applicationContext, i.toString(), Toast.LENGTH_SHORT).show()
                }

                if (i != routineList.size) {
                    //Set Selected the first not done bean
                    routineList[i].setSelected(true)

                    manageRoutine
                        .editRoutine(
                            ManageRoutineFacade
                                .beanToModel(routineList[i], selectedDay.name), applicationContext
                        )
                }
            }
        }

        //Remove Habit from the List
        routineList.removeAt(position)

        //Notify the Adapter
        routineAdapter.notifyDataSetChanged()
    }

    override fun editHabit(position: Int) {
        val bundle = Bundle()
        //Put the Routine to edit
        bundle.putSerializable("Edit Habit Details", routineList[position])
        //Put the Position of the Routine to edit
        bundle.putInt("Edit Routine Position", position)

        //Create the Details Fragment
        val habitDetailsEditFragment = RoutineDetailsEditFragment(this)
        habitDetailsEditFragment.arguments = bundle
        habitDetailsEditFragment.show(this.supportFragmentManager,"EditHabitDetailsFragment")
    }



    override fun updateList(position: Int, routine: RoutineBean) {
        //Remove the old Routine in the position
        routineList.removeAt(position)

        //Add the new edited Routine in the position
        routineList.add(position, routine)

        //Update the Adapter
        routineAdapter.notifyDataSetChanged()
    }
}