package com.example.straight_habits.controller.application

import android.content.Context
import com.example.straight_habits.database.RoomDB
import com.example.straight_habits.models.CategoryModel

class ManageCategories {
    //Add the Categories List into the DB
    suspend fun addCategoriesList(categories: MutableList<CategoryModel>, context: Context){
        //Get the DB Instance
        val db = RoomDB.getInstance(context)
        val dao = db.categoryDAO()

        //Loop to add in the DB the List
        for(category in categories) dao.insert(category)
    }


    //Delete the Passed Category
    suspend fun deleteCategory(category: CategoryModel, context: Context){
        //Get the Database Instance
        val db = RoomDB.getInstance(context)
        val dao = db.categoryDAO()

        //Remove
        dao.delete(category.getName())

        //Close the Connection
        //db.close()
    }


    //Delete all the Categories
    suspend fun deleteAllCategories(context: Context){
        //Get the Database Instance
        val db = RoomDB.getInstance(context)
        val dao = db.categoryDAO()

        dao.deleteAll()

        //Close the Connection
        //db.close()
    }


    //Edit Category
    suspend fun editCategory(category: CategoryModel, context: Context){
        //Get the Database Instance
        val db = RoomDB.getInstance(context)
        val dao = db.categoryDAO()

        dao.edit(category)
    }
}