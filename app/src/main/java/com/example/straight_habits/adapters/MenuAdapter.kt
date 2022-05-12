package com.example.straight_habits.adapters

import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.straight_habits.R
import com.example.straight_habits.interfaces.MenuItemClickInterface
import com.example.straight_habits.models.MenuModel

class MenuAdapter(
    private var menuList: MutableList<MenuModel>,
    private val menuItemClickInterface: MenuItemClickInterface
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    //Create the Personalized ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.container_menu, parent, false)

        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.setData(menuList[position])
    }

    override fun getItemCount(): Int {
        return menuList.size
    }



    //View Holder
    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        //Attributes
        private val menuItem: ConstraintLayout = itemView.findViewById(R.id.menu_container)
        private val image: ImageView = itemView.findViewById(R.id.img_menu)
        private val id: TextView = itemView.findViewById(R.id.txt_menu_id)
        private val selector: LinearLayout = itemView.findViewById(R.id.menu_item_selected)

        //Method
        fun setData(item: MenuModel){
            //Set Text
            id.text = item.getID()

            //Set Image
            if(item.getSelected())
                setSelected(item)
            else
                setNotSelected(item)

            //Set Click Listener
            menuItem.setOnClickListener{
                menuItemClickInterface.selectMenuItem(adapterPosition)
            }
        }

        private fun setSelected(item: MenuModel){
            //Image
            if(item.getID() == "Routines")
                image.setImageResource(R.drawable.icon_todo_blue)
            else if(item.getID() == "Habits")
                image.setImageResource(R.drawable.icon_habits_blue)
            else
                image.setImageResource(R.drawable.icon_stats_blue)

            //Text
            id.setTextColor(ContextCompat.getColor(itemView.context, R.color.blue))

            //Selector
            selector.setBackgroundResource(R.drawable.icon_menu_item_selected)
        }

        private fun setNotSelected(item: MenuModel){
            //Check Night ot Day Mode
            when (itemView.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                //Night Mode
                Configuration.UI_MODE_NIGHT_YES
                ->{
                    //Image
                    if(item.getID() == "Routines")
                        image.setImageResource(R.drawable.icon_todo_grey)
                    else if(item.getID() == "Habits")
                        image.setImageResource(R.drawable.icon_habits_grey)
                    else
                        image.setImageResource(R.drawable.icon_stats_grey)

                    //Text
                    id.setTextColor(ContextCompat.getColor(itemView.context, R.color.lite_text))
                }

                //Day Mode
                Configuration.UI_MODE_NIGHT_NO
                ->{
                    //Image
                    if(item.getID() == "Routines")
                        image.setImageResource(R.drawable.icon_todo_night)
                    else if(item.getID() == "Habits")
                        image.setImageResource(R.drawable.icon_habits_night)
                    else
                        image.setImageResource(R.drawable.icon_stats_night)

                    //Text
                    id.setTextColor(ContextCompat.getColor(itemView.context, R.color.dark_grey))
                }

                //Undefined
                Configuration.UI_MODE_NIGHT_UNDEFINED
                ->{
                    //Image
                    if(item.getID() == "Routines")
                        image.setImageResource(R.drawable.icon_todo_grey)
                    else if(item.getID() == "Habits")
                        image.setImageResource(R.drawable.icon_habits_grey)
                    else
                        image.setImageResource(R.drawable.icon_stats_grey)

                    //Text
                    id.setTextColor(ContextCompat.getColor(itemView.context, R.color.lite_text))
                }
            }

            //Selector
            selector.setBackgroundResource(R.drawable.icon_menu_item_not_selected)
        }
    }
}