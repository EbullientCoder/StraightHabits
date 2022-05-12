package com.example.straight_habits.adapters

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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditHabitsAdapter.EditHabitsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.container_edit_habit, parent, false)

        return EditHabitsViewHolder(view)
    }

    override fun onBindViewHolder(holder: EditHabitsAdapter.EditHabitsViewHolder, position: Int) {
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
            txtHabitName.text = habit.getName()
            txtInformation.text = habit.getInfo()
            txtFullDate.text = habit.getStartHour() + " - " + habit.getEndHour()
            txtShortHour.text = habit.getShortHour()
            txtCategory.text = habit.getCategory()

            //Set Buttons
            setButtons()

            //Check Empty
            setNormal()
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
        private fun setNormal(){
            //Text Color
            txtFullDate.setTextColor(ContextCompat.getColor(itemView.context, R.color.lite_text))
            txtShortHour.setTextColor(ContextCompat.getColor(itemView.context, R.color.lite_text))
            //Line Not Visible
            line.setBackgroundResource(R.color.lite_text)
            //Dot Not Visible
            imgDot.setImageResource(R.drawable.icon_dot_not_selected)
            //Remove Buttons
            btnEdit.visibility = View.VISIBLE
            btnDelete.visibility = View.VISIBLE
        }

    }
}