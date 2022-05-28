package com.example.straight_habits.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

//Room DB Entity
@Entity(tableName = "HabitTable")
data class RoutineModel(
    @PrimaryKey(autoGenerate = true)
    private var ID : Int,

    @ColumnInfo(name = "Name")
    private val name : String,

    @ColumnInfo(name = "Information")
    private val information : String,

    @ColumnInfo(name = "Category")
    private val category : String,

    @ColumnInfo(name = "Start")
    private val startHour : String,

    @ColumnInfo(name = "End")
    private val endHour : String,

    @ColumnInfo(name = "Day")
    private val day : String,

    @ColumnInfo(name = "Selected")
    private var selected : Boolean,

    @ColumnInfo(name = "Done")
    private var done : Boolean) : Serializable{

        //Getter
        fun getID() : Int{ return ID }
        fun getName() : String{ return name }
        fun getInformation() : String{ return information }
        fun getCategory() : String{ return category }
        fun getStartHour() : String{ return startHour }
        fun getEndHour() : String{ return endHour }
        fun getDay() : String{ return day }
        fun getSelected() : Boolean{ return selected }
        fun getDone() : Boolean{ return done }

        //Setter
        fun setSelected(selected: Boolean){ this.selected = selected }
        fun setDone(done: Boolean){ this.done = done }
    }