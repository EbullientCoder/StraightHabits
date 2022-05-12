package com.example.straight_habits.models

class MenuModel(private var selected: Boolean, private val ID: String){
    //Getter
    fun getSelected(): Boolean { return selected}
    fun getID(): String { return ID }

    //Setter
    fun setSelected(selected: Boolean){ this.selected = selected }
}