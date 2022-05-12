package com.example.straight_habits.controller.graphic


import android.content.res.Configuration
import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.straight_habits.activity.MainActivity
import com.example.straight_habits.R
import com.example.straight_habits.facade.ManageDaysFacade
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.straight_habits.adapters.MenuAdapter
import com.example.straight_habits.adapters.CategoriesAdapter
import com.example.straight_habits.database.RoomDB
import com.example.straight_habits.interfaces.MenuItemClickInterface
import com.example.straight_habits.interfaces.SelectCategoryInterface
import com.example.straight_habits.models.CategoryModel
import com.example.straight_habits.models.MenuModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
class MainGraphicController(view: MainActivity): SelectCategoryInterface, MenuItemClickInterface {
    private val mainInstance = view
    //Text
    private val txtDay : TextView
    private val txtDot : TextView
    //Menu Button
    private var btnMenu : ImageView
    //Add Habit Button
    private var btnAddHabit : FloatingActionButton

    //Categories
    private var rvCategories: RecyclerView
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var categoriesList: MutableList<CategoryModel>

    //Menu
    private var rvMenu: RecyclerView
    private var menuAdapter: MenuAdapter
    private var menuList: MutableList<MenuModel>



    //First block that will be executed
    init {
        //Remove the Action Bar
        mainInstance.supportActionBar?.hide()

        //Text Day and Dot
        txtDay = view.findViewById(R.id.txt_day)
        txtDay.text = ManageDaysFacade.getCurrentDate()
        txtDot = view.findViewById(R.id.txt_dot)

        //Buttons
        btnMenu = view.findViewById(R.id.btn_menu)
        btnAddHabit = view.findViewById(R.id.btn_add_habit)


        //Categories
        //RecyclerView
        rvCategories = view.findViewById(R.id.rv_categories)

        //Set Categories
        mainInstance.lifecycleScope.launch(Dispatchers.IO){
            //Get Categories
            getCategoriesList()

            //Set the Recycler View in the UI Thread
            mainInstance.runOnUiThread{
                //Set Recycler View
                setCategoriesRecyclerView()
            }
        }


        //Menu
        //Menu List
        menuList = ArrayList()
        menuList.add(MenuModel(true, "Routines"))
        menuList.add(MenuModel(false, "Habits"))
        menuList.add(MenuModel(false, "Statistics"))
        //Adapter
        menuAdapter = MenuAdapter(menuList, this)
        //RecyclerView
        rvMenu = view.findViewById(R.id.rv_menu)
        //Set RecyclerView Background
        //Check Night ot Day Mode
        when (mainInstance.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            //Night Mode
            Configuration.UI_MODE_NIGHT_YES
            ->{
                rvMenu.setBackgroundResource(R.color.menu_dark_background)
            }

            //Day Mode
            Configuration.UI_MODE_NIGHT_NO
            ->{
                rvMenu.setBackgroundResource(R.color.menu_background)
            }

            //Undefined
            Configuration.UI_MODE_NIGHT_UNDEFINED
            ->{
                rvMenu.setBackgroundResource(R.color.menu_background)
            }
        }

        rvMenu.layoutManager = LinearLayoutManager(mainInstance, LinearLayoutManager.HORIZONTAL, false)
        rvMenu.adapter = menuAdapter
    }




    //Categories
    //Get Categories
    private suspend fun getCategoriesList(){
        //Database Instance
        val DB = RoomDB.getInstance(mainInstance).categoryDAO()

        //Habits List
        categoriesList = DB.readAll()
    }

    //Set Recycler View
    private fun setCategoriesRecyclerView(){
        //Adapter
        categoriesAdapter = CategoriesAdapter(categoriesList, this)

        //Recycler View
        rvCategories.layoutManager = LinearLayoutManager(mainInstance, LinearLayoutManager.HORIZONTAL, false)
        rvCategories.adapter = categoriesAdapter
    }





    //Interfaces Methods----------------------------------------------------------------------------
    //Select Category Interface
    override fun selectCategory(position: Int) {
        //Deselect All
        for(i in 0 until categoriesList.size)
            categoriesList[i].setSelected(false)
        //Select One
        categoriesList[position].setSelected(true)

        //Notify the Adapter
        categoriesAdapter.notifyDataSetChanged()


        //Call the MainActivity method to update the Fragment showed list
        mainInstance.setFragment(categoriesList[position].getName())
    }



    //Menu Item Click Interface
    override fun selectMenuItem(position: Int) {
        //Deselect All
        menuList[0].setSelected(false)
        menuList[1].setSelected(false)
        menuList[2].setSelected(false)

        //Select Menu Item
        menuList[position].setSelected(true)


        //Notify the Adapter
        menuAdapter.notifyDataSetChanged()
    }
}