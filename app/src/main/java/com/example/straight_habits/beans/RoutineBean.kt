package com.example.straight_habits.beans

import java.io.Serializable
import java.util.*

class RoutineBean(
    private val ID: Int,
    private var name : String,
    private var information : String,
    private var category : String,
    private var startHour : String,
    private var endHour : String,
    private var selected : Boolean,
    private var done : Boolean) : Serializable{

    //Attribute to get the start Hour
    private var shortHour : String

    // initializer block
    init {
        //Short Hour
        shortHour = if(startHour.contains(":")){
            val st = StringTokenizer(startHour, ":")
            st.nextToken() + ":00"
        } else ""

        //Duration
        //Start Hour
        val start = StringTokenizer(startHour, ":")
        //End Hour
        val end = StringTokenizer(endHour, ":")
        //Time
        var hours = end.nextToken().toInt() - start.nextToken().toInt()
        var minutes = end.nextToken().toInt() - start.nextToken().toInt()
        if(minutes < 0){
            hours--
            minutes += 60
        }
    }

    //Getter
    fun getID(): Int{ return ID}
    fun getName() : String{ return name}
    fun getInformation() : String{ return information}
    fun getCategory() : String{ return category}
    fun getStartHour() : String{ return startHour}
    fun getEndHour() : String{ return endHour}
    fun getSelected() : Boolean{ return selected}
    fun getDone() : Boolean{ return done}
    fun getShortHour() : String{ return shortHour}

    //Setter
    fun setName(name: String){ this.name = name}
    fun setInformation(information: String){ this.information = information}
    fun setCategory(category: String){ this.category = category}
    fun setStart(start: String){ this.startHour = start}
    fun setEnd(end: String){ this.endHour = end}
    fun setSelected(state : Boolean){ selected = state}
    fun setDone(state : Boolean){ done = state}
}