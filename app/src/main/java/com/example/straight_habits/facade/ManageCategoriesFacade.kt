package com.example.straight_habits.facade

import com.example.straight_habits.beans.HabitBean
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


        //Get the Position
        fun getSelectedPosition(categories: MutableList<CategoryModel>) : Int{
            //Search the Selected Habit
            if(categories.size == 0)
                return 0

            if(categories.size != 0){
                for(i in 0 until categories.size)
                    if(categories[i].getSelected())
                        return i
            }

            return categories.size - 1
        }
    }
}