package com.example.straight_habits.beans

import java.io.Serializable
import java.util.*

class HabitBean(
    private val ID: Int,
    private val name : String,
    private var information : String,
    private var category : String,
    private val startHour : String,
    private val endHour : String,
    private var selected : Boolean,
    private var done : Boolean) : Serializable{

    //Attribute to get the start Hour
    private var shortHour : String
    private var info : String

    // initializer block
    init {
        //Short Hour
        shortHour = if(startHour.contains(":")){
            val st = StringTokenizer(startHour, ":")
            st.nextToken() + ":00"
        } else ""

        //Information
        if(information.length > 40){
            info = information.subSequence(0, 40).toString() + "-\n" + information.subSequence(40, information.length).toString()

            if(info.length > 80)
                info = info.subSequence(0, 77).toString() + "..."
        }
        else
            info = information
    }

    //Getter
    fun getID(): Int{ return ID}
    fun getName() : String{ return name}
    fun getInfo() : String{ return info}
    fun getInformation() : String{ return information}
    fun getCategory() : String{ return category}
    fun getStartHour() : String{ return startHour}
    fun getEndHour() : String{ return endHour}
    fun getSelected() : Boolean{ return selected}
    fun getDone() : Boolean{ return done}
    fun getShortHour() : String{ return shortHour}

    //Setter
    fun setSelected(state : Boolean){ selected = state}
    fun setDone(state : Boolean){ done = state}
    fun setShortHour(hour : String){ shortHour = hour}
}