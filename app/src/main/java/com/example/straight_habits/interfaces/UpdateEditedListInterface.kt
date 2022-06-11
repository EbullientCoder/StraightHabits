package com.example.straight_habits.interfaces

import com.example.straight_habits.beans.RoutineBean
import com.example.straight_habits.models.CategoryModel
import com.example.straight_habits.models.RoutineModel

interface UpdateEditedListInterface {
    fun updateRoutineList(position: Int, routineModel: RoutineBean)
    fun updateCategoryList(position: Int, categoryModel: CategoryModel)
}