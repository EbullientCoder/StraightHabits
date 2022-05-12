package com.example.straight_habits.facade

import com.example.straight_habits.models.CategoryModel

class ManageCategoriesFacade {
    companion object{
        fun createCategoriesList(): MutableList<CategoryModel>{
            var categoriesList: MutableList<CategoryModel> = ArrayList()

            categoriesList.add(CategoryModel(0,"Morning", true))
            categoriesList.add(CategoryModel(0,"Afternoon", false))
            categoriesList.add(CategoryModel(0,"Evening", false))
            categoriesList.add(CategoryModel(0,"Night", false))

            return categoriesList
        }
    }
}