package com.example.straight_habits.interfaces.routine

interface RoutineDetailsInterface {
    //Open the Dialog Fragment when the Habit is clicked
    fun openHabitDetails(position: Int)

    //Open the Dialog Fragment when the Habit is long clicked
    fun openRoutineEditDeletePopup(position: Int)
}