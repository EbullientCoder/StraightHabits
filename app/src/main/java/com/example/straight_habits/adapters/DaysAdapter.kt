package com.example.straight_habits.adapters

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.straight_habits.R
import com.example.straight_habits.adapters.categories.EditCategoriesAdapter
import com.example.straight_habits.interfaces.SelectCategoryInterface
import com.example.straight_habits.models.CategoryModel

class DaysAdapter(
    private val days: MutableList<String>
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

        //Method
        fun setData(day: String){
            txtDay.text = day
        }

        //Selected

    }
}