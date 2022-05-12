package com.example.straight_habits.beans

class CategoryBean(private val name: String,
                   private var selected: Boolean,
                   private var habitsNumber: Int) {
    //Getter
    fun getName(): String{ return name }
    fun getSelected(): Boolean{ return selected }
    fun getHabitsNumber(): Int{ return habitsNumber }

    //Setter
    fun setSelected(selected: Boolean){ this.selected = selected }
    fun setHabitsNumber(habitsNumber: Int){ this.habitsNumber = habitsNumber }
}