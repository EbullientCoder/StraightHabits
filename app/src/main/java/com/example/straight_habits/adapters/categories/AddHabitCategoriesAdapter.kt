package com.example.straight_habits.adapters.categories


import android.content.res.Configuration
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.straight_habits.R
import com.example.straight_habits.database.RoomDB
import com.example.straight_habits.facade.ManageDaysFacade
import com.example.straight_habits.interfaces.SelectCategoryInterface
import com.example.straight_habits.models.CategoryModel
import kotlinx.coroutines.runBlocking



class AddHabitCategoriesAdapter(
    private val categories: MutableList<CategoryModel>,
    private val selectCategoryInterface: SelectCategoryInterface
): RecyclerView.Adapter<AddHabitCategoriesAdapter.CategoryViewHolder>()  {

    //Personalized View Holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.container_category_add_habit, parent, false)

        return CategoryViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.setCommonData(categories[position])
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    //View Holder
    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        //Attributes
        //Name
        private val txtName = itemView.findViewById<TextView>(R.id.txt_category_name)
        //Background
        private val categoryBackground = itemView.findViewById<ConstraintLayout>(R.id.layout_category_container)


        //Methods
        @RequiresApi(Build.VERSION_CODES.O)
        fun setCommonData(category: CategoryModel){
            //Click Listener
            txtName.setOnClickListener{
                selectCategoryInterface.selectCategory(adapterPosition)
            }
            categoryBackground.setOnClickListener{
                selectCategoryInterface.selectCategory(adapterPosition)
            }

            //Set Name
            txtName.text = category.getName()


            //Set Selected
            if(category.getSelected()){
                //Set Text Color
                txtName.setTextColor(ContextCompat.getColor(itemView.context, R.color.blue))
                //Set Background
                categoryBackground.setBackgroundResource(R.drawable.category_container_background)
            }
            else{
                //Check Night ot Day Mode
                when (itemView.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                    //Night Mode
                    Configuration.UI_MODE_NIGHT_YES
                    ->{
                        //Set Background
                        categoryBackground.setBackgroundResource(R.color.dark_background)
                        //Set Text Color
                        txtName.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                    }

                    //Day Mode
                    Configuration.UI_MODE_NIGHT_NO
                    ->{
                        //Set Background
                        categoryBackground.setBackgroundResource(R.color.background)
                        //Set Text Color
                        txtName.setTextColor(ContextCompat.getColor(itemView.context, R.color.dark_text))
                    }

                    //Undefined
                    Configuration.UI_MODE_NIGHT_UNDEFINED
                    ->{
                        //Set Background
                        categoryBackground.setBackgroundResource(R.color.background)
                        //Set Text Color
                        txtName.setTextColor(ContextCompat.getColor(itemView.context, R.color.dark_text))
                    }
                }
            }
        }

    }
}