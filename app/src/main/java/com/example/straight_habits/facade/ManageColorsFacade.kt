package com.example.straight_habits.facade

import android.content.Context
import android.graphics.Paint
import android.util.TypedValue
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.straight_habits.R

class ManageColorsFacade {
    companion object{

        //Set Adapter's Colors
        fun setAdapterColors(habitName : TextView, shortHour : TextView, 
                             fullDate : TextView, information : TextView,
                             dot : ImageView, line : LinearLayout, selected : Boolean, context : Context){

            //Dark & Yellow Colors Scheme
            habitName.paintFlags = habitName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

            if(selected){
                //Text Size
                habitName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                //Text Color
                habitName.setTextColor(ContextCompat.getColor(context, R.color.main_text))
                shortHour.setTextColor(ContextCompat.getColor(context, R.color.main_text))
                fullDate.setTextColor(ContextCompat.getColor(context, R.color.main_text))
                information.setTextColor(ContextCompat.getColor(context, R.color.main_text))
                //Dot Img
                dot.setImageResource(R.drawable.icon_dot_selected)
                //Set Line
                line.setBackgroundResource(R.color.main_text)
            }
            else{
                //Text Size
                habitName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                //Text Color
                habitName.setTextColor(ContextCompat.getColor(context, R.color.lite_text))
                shortHour.setTextColor(ContextCompat.getColor(context, R.color.lite_text))
                fullDate.setTextColor(ContextCompat.getColor(context, R.color.lite_text))
                information.setTextColor(ContextCompat.getColor(context, R.color.lite_text))
                //Dot Img
                dot.setImageResource(R.drawable.icon_dot_not_selected)
                //Set Line
                line.setBackgroundResource(R.color.lite_text)
            }
        }
    }
}