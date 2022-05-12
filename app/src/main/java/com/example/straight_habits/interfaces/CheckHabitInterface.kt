package com.example.straight_habits.interfaces

interface CheckHabitInterface {
    //Select the next Habit and mark the current as done
    fun checkHabit(position : Int)
    //Mark the current as done
    fun checkLastHabit(position: Int)
    //Mark the clicked habit as done
    fun preDoneHabit(position: Int, clicked : Boolean)
}