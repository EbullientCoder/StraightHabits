package com.example.straight_habits.adapters.days

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.straight_habits.R
import com.example.straight_habits.adapters.routine.EditRoutineAdapter
import com.example.straight_habits.interfaces.SelectDayInterface
import com.example.straight_habits.models.DayModel

class EditRoutineDaysAdapter(
    private var daysList: MutableList<DayModel>,
    private var selectDayInterface: SelectDayInterface
    ) : RecyclerView.Adapter<EditRoutineDaysAdapter.DaysViewHolder>() {


    //Create the Personalized ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditRoutineDaysAdapter.DaysViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.container_day, parent, false)

        return DaysViewHolder(view)
    }

    override fun onBindViewHolder(holder: EditRoutineDaysAdapter.DaysViewHolder, position: Int) {
        holder.setCommonData(daysList[position])
    }

    override fun getItemCount(): Int {
        return daysList.size
    }



    //View Holder
    inner class DaysViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        //Attributes
        private val txtDay = itemView.findViewById<TextView>(R.id.txt_day_name)
        private val background = itemView.findViewById<ConstraintLayout>(R.id.layout_day_container)

        //Methods
        fun setCommonData(day: DayModel){
            //Set Text
            txtDay.text = day.name

            //Selected
            if(day.selected) setSelected()
            else setNotSelected()

            //Click Listener
            background.setOnClickListener {
                selectDayInterface.selectDay(adapterPosition)
            }
        }


        //Set Selected
        private fun setSelected(){
            //Text Color
            txtDay.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
            //Background
            background.setBackgroundResource(R.drawable.category_edit_container_background)
        }

        //Set not Selected
        private fun setNotSelected(){
            //Text Color
            txtDay.setTextColor(ContextCompat.getColor(itemView.context, R.color.dark_grey))
            //Background
            background.setBackgroundResource(R.color.dark_background)
        }
    }
}