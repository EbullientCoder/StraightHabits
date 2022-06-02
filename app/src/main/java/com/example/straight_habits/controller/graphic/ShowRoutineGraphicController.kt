package com.example.straight_habits.controller.graphic


import android.content.res.Configuration
import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.straight_habits.activity.routine.ShowRoutineActivity
import com.example.straight_habits.R
import com.example.straight_habits.facade.ManageDaysFacade
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.straight_habits.adapters.MenuAdapter
import com.example.straight_habits.interfaces.MenuItemClickInterface
import com.example.straight_habits.models.MenuModel


@RequiresApi(Build.VERSION_CODES.O)
class ShowRoutineGraphicController(view: ShowRoutineActivity): MenuItemClickInterface {
    private val mainInstance = view
    //Text
    private val txtDay : TextView
    private val txtDot : TextView
    //Menu Button
    private var btnMenu : ImageView
    //Add Habit Button
    private var btnAddHabit : FloatingActionButton

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





    //Interfaces Methods----------------------------------------------------------------------------
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