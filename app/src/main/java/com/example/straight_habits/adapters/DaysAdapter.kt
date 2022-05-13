package com.example.straight_habits.adapters

import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.straight_habits.R
import com.example.straight_habits.interfaces.SelectDayInterface
import com.example.straight_habits.models.DayModel


class DaysAdapter(
    private val days: MutableList<DayModel>,
    private val selectDayInterface: SelectDayInterface
): RecyclerView.Adapter<DaysAdapter.DayViewHolder>()  {

    //Personalized View Holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysAdapter.DayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.container_day, parent, false)

        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DaysAdapter.DayViewHolder, position: Int) {
        holder.setData(days[position])
    }

    override fun getItemCount(): Int {
        return days.size
    }


    //View Holder
    inner class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        //Text
        private val txtDay = itemView.findViewById<TextView>(R.id.txt_day_name)
        //Background
        private var background = itemView.findViewById<ConstraintLayout>(R.id.day_background)

        //Method
        fun setData(day: DayModel){
            //Set Click Listener
            txtDay.setOnClickListener{
                selectDayInterface.selectDay(adapterPosition)
            }
            background.setOnClickListener {
                selectDayInterface.selectDay(adapterPosition)
            }


            txtDay.text = day.name

            //Selected
            if(day.selected)
                setSelected()
            else
                setNotSelected()
        }

        //Selected
        private fun setSelected(){
            //Text Color
            txtDay.setTextColor(ContextCompat.getColor(itemView.context, R.color.blue))
            //Background Color
            background.setBackgroundResource(R.drawable.day_container_background)
        }

        //Not Selected
        private fun setNotSelected(){
            //Check Night ot Day Mode
            when (itemView.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                //Night Mode
                Configuration.UI_MODE_NIGHT_YES
                ->{
                    //Text Color
                    txtDay.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                    //Background Color
                    background.setBackgroundResource(R.color.dark_background)
                }

                //Day Mode
                Configuration.UI_MODE_NIGHT_NO
                ->{
                    //Text Color
                    txtDay.setTextColor(ContextCompat.getColor(itemView.context, R.color.dark_text))
                    //Background Color
                    background.setBackgroundResource(R.color.background)
                }

                //Undefined
                Configuration.UI_MODE_NIGHT_UNDEFINED
                ->{
                    //Text Color
                    txtDay.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                    //Background Color
                    background.setBackgroundResource(R.color.dark_background)
                }
            }
        }
    }
}