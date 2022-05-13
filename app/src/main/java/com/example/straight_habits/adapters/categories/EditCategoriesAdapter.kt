package com.example.straight_habits.adapters.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.straight_habits.R
import com.example.straight_habits.interfaces.SelectCategoryInterface
import com.example.straight_habits.models.CategoryModel

class EditCategoriesAdapter(
    private val categories: MutableList<CategoryModel>,
    private val selectCategoryInterface: SelectCategoryInterface
): RecyclerView.Adapter<EditCategoriesAdapter.EditCategoryViewHolder>()  {

    //Personalized View Holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditCategoriesAdapter.EditCategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.container_edit_category, parent, false)

        return EditCategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: EditCategoriesAdapter.EditCategoryViewHolder, position: Int) {
        holder.setData(categories[position])
    }

    override fun getItemCount(): Int {
        return categories.size
    }



    //View Holder
    inner class EditCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        //Attributes
        //Name
        private val txtName = itemView.findViewById<TextView>(R.id.txt_category_name)
        //Background
        private val categoryBackground = itemView.findViewById<ConstraintLayout>(R.id.layout_category_container)
        //Habits Left
        //private val txtHabitsLeft = itemView.findViewById<TextView>(R.id.txt_habits_left)
        //Habits Counter
        //private val habitsCounter = itemView.findViewById<ConstraintLayout>(R.id.habits_left_background)


        //Methods
        fun setData(category: CategoryModel){
            //Click Listener
            txtName.setOnClickListener{
                selectCategoryInterface.selectCategory(adapterPosition)
            }
            categoryBackground.setOnClickListener{
                selectCategoryInterface.selectCategory(adapterPosition)
            }

            //Set Name
            txtName.text = category.getName()
        }
    }
}