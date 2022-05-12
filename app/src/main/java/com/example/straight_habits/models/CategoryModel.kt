package com.example.straight_habits.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

//Room DB Entity
@Entity(tableName = "CategoryTable")
class CategoryModel(
    @PrimaryKey(autoGenerate = true)
    private var ID : Int,

    @ColumnInfo(name = "Name")
    private val name: String,

    @ColumnInfo(name = "Selected")
    private var selected: Boolean): Serializable{
        //Getter
        fun getID() : Int{ return ID }
        fun getName(): String{ return name }
        fun getSelected(): Boolean{ return selected }

        //Setter
        fun setSelected(selected: Boolean){ this.selected = selected }
}