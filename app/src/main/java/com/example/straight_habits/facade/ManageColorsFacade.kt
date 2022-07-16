package com.example.straight_habits.facade

import android.content.Context
import android.content.res.Configuration
import android.graphics.Paint
import android.util.TypedValue
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.example.straight_habits.R
import android.content.res.TypedArray




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
                habitName.setTextColor(ContextCompat.getColor(context, R.color.white))
                shortHour.setTextColor(ContextCompat.getColor(context, R.color.white))
                fullDate.setTextColor(ContextCompat.getColor(context, R.color.white))
                information.setTextColor(ContextCompat.getColor(context, R.color.white))

                //Set Line
                line.setBackgroundResource(R.color.white)

                //Check Night ot Day Mode
                /*when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                    //Night Mode
                    Configuration.UI_MODE_NIGHT_YES
                    ->{
                        //Text Color
                        habitName.setTextColor(ContextCompat.getColor(context, R.color.white))
                        shortHour.setTextColor(ContextCompat.getColor(context, R.color.white))
                        fullDate.setTextColor(ContextCompat.getColor(context, R.color.white))
                        information.setTextColor(ContextCompat.getColor(context, R.color.white))

                        //Set Line
                        line.setBackgroundResource(R.color.white)
                    }

                    //Day Mode
                    Configuration.UI_MODE_NIGHT_NO
                    ->{
                        //Text Color
                        habitName.setTextColor(ContextCompat.getColor(context, R.color.dark_text))
                        shortHour.setTextColor(ContextCompat.getColor(context, R.color.dark_text))
                        fullDate.setTextColor(ContextCompat.getColor(context, R.color.dark_text))
                        information.setTextColor(ContextCompat.getColor(context, R.color.dark_text))

                        //Set Line
                        line.setBackgroundResource(R.color.dark_text)
                    }

                    //Undefined
                    Configuration.UI_MODE_NIGHT_UNDEFINED
                    ->{
                        //Text Color
                        habitName.setTextColor(ContextCompat.getColor(context, R.color.dark_text))
                        shortHour.setTextColor(ContextCompat.getColor(context, R.color.dark_text))
                        fullDate.setTextColor(ContextCompat.getColor(context, R.color.dark_text))
                        information.setTextColor(ContextCompat.getColor(context, R.color.dark_text))

                        //Set Line
                        line.setBackgroundResource(R.color.dark_text)
                    }
                }*/

                //Dot Img
                dot.setImageResource(R.drawable.icon_dot_selected)
            }
            else{
                //Text Size
                habitName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)

                //Text Color
                habitName.setTextColor(ContextCompat.getColor(context, R.color.dark_grey))
                shortHour.setTextColor(ContextCompat.getColor(context, R.color.dark_grey))
                fullDate.setTextColor(ContextCompat.getColor(context, R.color.dark_grey))
                information.setTextColor(ContextCompat.getColor(context, R.color.dark_grey))

                //Set Line
                line.setBackgroundResource(R.color.dark_grey)


                //Check Night ot Day Mode
                /*when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                    //Night Mode
                    Configuration.UI_MODE_NIGHT_YES
                    ->{
                        //Text Color
                        habitName.setTextColor(ContextCompat.getColor(context, R.color.dark_grey))
                        shortHour.setTextColor(ContextCompat.getColor(context, R.color.dark_grey))
                        fullDate.setTextColor(ContextCompat.getColor(context, R.color.dark_grey))
                        information.setTextColor(ContextCompat.getColor(context, R.color.dark_grey))

                        //Set Line
                        line.setBackgroundResource(R.color.dark_grey)
                    }

                    //Day Mode
                    Configuration.UI_MODE_NIGHT_NO
                    ->{
                        //Text Color
                        habitName.setTextColor(ContextCompat.getColor(context, R.color.lite_text))
                        shortHour.setTextColor(ContextCompat.getColor(context, R.color.lite_text))
                        fullDate.setTextColor(ContextCompat.getColor(context, R.color.lite_text))
                        information.setTextColor(ContextCompat.getColor(context, R.color.lite_text))

                        //Set Line
                        line.setBackgroundResource(R.color.lite_text)
                    }

                    //Undefined
                    Configuration.UI_MODE_NIGHT_UNDEFINED
                    ->{
                        //Text Color
                        habitName.setTextColor(ContextCompat.getColor(context, R.color.lite_text))
                        shortHour.setTextColor(ContextCompat.getColor(context, R.color.lite_text))
                        fullDate.setTextColor(ContextCompat.getColor(context, R.color.lite_text))
                        information.setTextColor(ContextCompat.getColor(context, R.color.lite_text))

                        //Set Line
                        line.setBackgroundResource(R.color.lite_text)
                    }
                }*/

                //Dot Img
                dot.setImageResource(R.drawable.icon_dot_not_selected)
            }
        }
    }
}