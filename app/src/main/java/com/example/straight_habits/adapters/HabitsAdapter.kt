package com.example.straight_habits.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.straight_habits.R
import com.example.straight_habits.beans.HabitBean
import com.example.straight_habits.facade.ManageColorsFacade
import com.example.straight_habits.facade.ManageHabitsFacade
import com.example.straight_habits.interfaces.CheckHabitInterface
import com.example.straight_habits.interfaces.HabitDetailsInterface

class HabitsAdapter(
    private var habitsList : MutableList<HabitBean>,
    private var checkHabitInterface : CheckHabitInterface,
    private var habitDetailsInterface: HabitDetailsInterface
) : RecyclerView.Adapter<HabitsAdapter.HabitsViewHolder>() {

    //Create the Personalized ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): HabitsAdapter.HabitsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.container_habit, parent, false)

        return HabitsViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitsViewHolder, position: Int) {
        var selectedPosition = ManageHabitsFacade.getSelectedPosition(habitsList)
        holder.setCommonData(habitsList[position], selectedPosition)

        if(position == 0 ||
            habitsList[position - 1].getShortHour() != habitsList[position].getShortHour()
        )
                holder.setHour(habitsList[position])
    }

    override fun getItemCount(): Int {
        return habitsList.size
    }


    //View Holder
    inner class HabitsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val txtHabitName = itemView.findViewById<TextView>(R.id.txt_habit_name)
        private val txtShortHour = itemView.findViewById<TextView>(R.id.txt_left_date)
        private val txtFullDate = itemView.findViewById<TextView>(R.id.txt_date)
        private val txtInformation = itemView.findViewById<TextView>(R.id.txt_information)
        private val btnDoneSelected = itemView.findViewById<CheckBox>(R.id.btn_done_selected)
        private val btnDoneNotSelected = itemView.findViewById<CheckBox>(R.id.btn_done_not_selected)
        private val imgDot = itemView.findViewById<ImageView>(R.id.img_dot)
        private val line = itemView.findViewById<LinearLayout>(R.id.line)
        private val container = itemView.findViewById<ConstraintLayout>(R.id.menu_container)

        //Methods
        fun setCommonData(habit : HabitBean, selectedPosition : Int){
            //Habit Selected or Not Selected
            if(habit.getSelected())
                habitSelected()
            else
                habitNotSelected(habit)

            //Habit Done
            if(habit.getDone() && adapterPosition < selectedPosition)
                habitDone()

            //Check Empty
            if(habit.getEmpty())
                setEmpty()

            //Set Text
            setText(habit)

            //Set Short Hour
            txtShortHour.visibility = View.GONE

            //Set Button
            setButton()

            //Habits Details
            habitDetails()
        }

        //Habit Selected
        private fun habitSelected(){
            //Set Text, Dot and Line Color and Size
            ManageColorsFacade.setAdapterColors(txtHabitName,
                txtShortHour,
                txtFullDate,
                txtInformation,
                imgDot,
                line,
                true,
                itemView.context)

            //Set Buttons
            btnDoneNotSelected.visibility = View.GONE
            btnDoneSelected.visibility = View.VISIBLE
            btnDoneSelected.isChecked = false
        }

        private fun habitNotSelected(habit: HabitBean){
            //Set Text, Dot and Line Color and Size
            ManageColorsFacade.setAdapterColors(txtHabitName,
                txtShortHour,
                txtFullDate,
                txtInformation,
                imgDot,
                line,
                false,
                itemView.context)

            //Set Button
            btnDoneNotSelected.visibility = View.VISIBLE
            btnDoneSelected.visibility = View.GONE
            btnDoneNotSelected.isChecked = false
            btnDoneNotSelected.isEnabled = true

            //Button Control Check
            if(habit.getDone()) btnDoneNotSelected.isChecked = true
        }

        //Habit Done
        private fun habitDone(){
            //Line the Habit
            txtHabitName.paintFlags = txtHabitName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            //Button Checked
            btnDoneNotSelected.isChecked = true
            //btnDoneNotSelected.isClickable = false
            btnDoneNotSelected.isEnabled = false
        }

        //Set Text
        private fun setText(habit : HabitBean){
            txtHabitName.text = habit.getName()
            txtFullDate.text = habit.getStartHour() + " - " + habit.getEndHour()
            txtInformation.text = habit.getInfo()
        }

        //Set Hour
        fun setHour(habit: HabitBean){
            txtShortHour.visibility = View.VISIBLE
            txtShortHour.text = habit.getShortHour()
        }

        //Set the Buttons Click Listener
        private fun setButton(){
            //Selected
            btnDoneSelected.setOnClickListener{
                //Check that the Item Clicked is not the Last
                if(adapterPosition != habitsList.size - 1){
                    //Select the Next Habit
                    checkHabitInterface.checkHabit(adapterPosition + 1)
                }
                else{
                    //Select the Last Habit
                    checkHabitInterface.checkLastHabit(adapterPosition)
                }
            }

            //Not Selected
            btnDoneNotSelected.setOnClickListener{
                if(btnDoneNotSelected.isChecked)
                    checkHabitInterface.preDoneHabit(adapterPosition, true)
                else
                    checkHabitInterface.preDoneHabit(adapterPosition, false)
            }
        }

        //Open Habit's Detail
        private fun habitDetails(){
            //Open Interface Method
            container.setOnClickListener {
                habitDetailsInterface.openHabitDetails(adapterPosition)
            }
        }


        //Empty
        private fun setEmpty(){
            //Text Color
            txtFullDate.setTextColor(ContextCompat.getColor(itemView.context, R.color.background))
            //Button Not Visible
            btnDoneSelected.visibility = View.GONE
            btnDoneNotSelected.visibility = View.GONE
            //Line Not Visible
            line.setBackgroundResource(R.color.background)
            //Dot Not Visible
            imgDot.setImageResource(R.drawable.icon_dot_not_visible)
        }
    }

}