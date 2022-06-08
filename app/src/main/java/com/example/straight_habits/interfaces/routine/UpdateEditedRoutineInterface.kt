package com.example.straight_habits.interfaces.routine

import com.example.straight_habits.beans.RoutineBean
import com.example.straight_habits.models.RoutineModel

interface UpdateEditedRoutineInterface {
    fun updateList(position: Int, routineModel: RoutineBean)
}