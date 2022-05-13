package com.example.straight_habits.adapters.habits

import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.straight_habits.R
import com.example.straight_habits.beans.HabitBean
import com.example.straight_habits.interfaces.EditHabitInterface


class EditHabitsAdapter(
    private var habitsList : MutableList<HabitBean>,
    private var editHabitInterface: EditHabitInterface
    ) : RecyclerView.Adapter<EditHabitsAdapter.EditHabitsViewHolder>() {

    //Create the Personalized ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditHabitsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.container_edit_habit, parent, false)

        return EditHabitsViewHolder(view)
    }

    override fun onBindViewHolder(holder: EditHabitsViewHolder, position: Int) {
        holder.setCommonData(habitsList[position])
    }

    override fun getItemCount(): Int {
        return habitsList.size
    }



    //View Holder
    inner class EditHabitsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        //Attributes
        private val txtHabitName = itemView.findViewById<TextView>(R.id.txt_habit_name)
        private val txtInformation = itemView.findViewById<TextView>(R.id.txt_information)
        private val txtFullDate = itemView.findViewById<TextView>(R.id.txt_date)
        private val txtShortHour = itemView.findViewById<TextView>(R.id.txt_left_date)
        private val txtCategory = itemView.findViewById<TextView>(R.id.txt_category)
        private val imgDot = itemView.findViewById<ImageView>(R.id.img_dot)
        private val line = itemView.findViewById<LinearLayout>(R.id.line)
        private val btnEdit = itemView.findViewById<ImageView>(R.id.btn_edit_habit)
        private val btnDelete = itemView.findViewById<ImageView>(R.id.btn_delete_habit)

        //Methods
        fun setCommonData(habit: HabitBean){
            //Text
            txtHabitName.text = habit.getName()
            txtInformation.text = habit.getInfo()
            txtFullDate.text = habit.getStartHour() + " - " + habit.getEndHour()
            txtShortHour.text = habit.getShortHour()
            txtCategory.text = habit.getCategory()

            //Set Buttons
            setButtons()

            //Check Empty
            setData()
        }

        //Set Buttons
        private fun setButtons(){
            btnDelete.setOnClickListener{
                editHabitInterface.deleteHabit(adapterPosition)
            }
            btnEdit.setOnClickListener{
                editHabitInterface.editHabit(adapterPosition)
            }
        }

        //Set Normal
        private fun setData(){
            //Check Night ot Day Mode
            when (itemView.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                //Night Mode
                Configuration.UI_MODE_NIGHT_YES
                ->{
                    //Text Color
                    txtHabitName.setTextColor(ContextCompat.getColor(itemView.context, R.color.dark_grey))
                    txtInformation.setTextColor(ContextCompat.getColor(itemView.context, R.color.dark_grey))
                    txtCategory.setTextColor(ContextCompat.getColor(itemView.context, R.color.dark_grey))
                    txtFullDate.setTextColor(ContextCompat.getColor(itemView.context, R.color.dark_grey))
                    txtShortHour.setTextColor(ContextCompat.getColor(itemView.context, R.color.dark_grey))
                    //Button Color
                    btnDelete.setImageResource(R.drawable.icon_delete_night)
                    btnEdit.setImageResource(R.drawable.icon_edit_night)
                    //Line Not Visible
                    line.setBackgroundResource(R.color.dark_grey)
                }

                //Day Mode
                Configuration.UI_MODE_NIGHT_NO
                ->{
                    //Text Color
                    txtHabitName.setTextColor(ContextCompat.getColor(itemView.context, R.color.lite_text))
                    txtInformation.setTextColor(ContextCompat.getColor(itemView.context, R.color.lite_text))
                    txtCategory.setTextColor(ContextCompat.getColor(itemView.context, R.color.lite_text))
                    txtFullDate.setTextColor(ContextCompat.getColor(itemView.context, R.color.lite_text))
                    txtShortHour.setTextColor(ContextCompat.getColor(itemView.context, R.color.lite_text))
                    //Button Color
                    btnDelete.setImageResource(R.drawable.icon_delete_grey)
                    btnEdit.setImageResource(R.drawable.icon_edit_grey)
                    //Line Not Visible
                    line.setBackgroundResource(R.color.lite_text)
                }

                //Undefined
                Configuration.UI_MODE_NIGHT_UNDEFINED
                ->{
                    //Text Color
                    txtHabitName.setTextColor(ContextCompat.getColor(itemView.context, R.color.lite_text))
                    txtInformation.setTextColor(ContextCompat.getColor(itemView.context, R.color.lite_text))
                    txtCategory.setTextColor(ContextCompat.getColor(itemView.context, R.color.lite_text))
                    txtFullDate.setTextColor(ContextCompat.getColor(itemView.context, R.color.lite_text))
                    txtShortHour.setTextColor(ContextCompat.getColor(itemView.context, R.color.lite_text))
                    //Button Color
                    btnDelete.setImageResource(R.drawable.icon_delete_grey)
                    btnEdit.setImageResource(R.drawable.icon_edit_grey)
                    //Line Not Visible
                    line.setBackgroundResource(R.color.lite_text)
                }
            }

            //Dot Not Visible
            imgDot.setImageResource(R.drawable.icon_dot_not_selected)
            //Remove Buttons
            btnEdit.visibility = View.VISIBLE
            btnDelete.visibility = View.VISIBLE
        }

    }
}