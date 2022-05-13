package com.example.straight_habits.adapters.categories

import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
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
        //Buttons
        private val btnEdit = itemView.findViewById<ImageView>(R.id.btn_edit_category)
        //private val btnDelete = itemView.findViewById<ImageView>(R.id.btn_delete_category)


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


            //Check Night ot Day Mode
            when (itemView.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                //Night Mode
                Configuration.UI_MODE_NIGHT_YES
                ->{
                    //Button Image
                    //btnDelete.setImageResource(R.drawable.icon_delete_night)
                    btnEdit.setImageResource(R.drawable.icon_edit_night)
                }

                //Day Mode
                Configuration.UI_MODE_NIGHT_NO
                ->{
                    //Button Image
                    //btnDelete.setImageResource(R.drawable.icon_delete_grey)
                    btnEdit.setImageResource(R.drawable.icon_edit_grey)
                }

                //Undefined
                Configuration.UI_MODE_NIGHT_UNDEFINED
                ->{
                    //Button Image
                    //btnDelete.setImageResource(R.drawable.icon_delete_night)
                    btnEdit.setImageResource(R.drawable.icon_edit_night)
                }
            }



            //Selected
            if(category.getSelected())
                setSelected()
            else
                setNotSelected()
        }

        //Category Selected
        private fun setSelected(){
            //Check Night ot Day Mode
            when (itemView.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                //Night Mode
                Configuration.UI_MODE_NIGHT_YES
                ->{
                    //Text Color
                    txtName.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                }

                //Day Mode
                Configuration.UI_MODE_NIGHT_NO
                ->{
                    //Text Color
                    txtName.setTextColor(ContextCompat.getColor(itemView.context, R.color.dark_text))
                }

                //Undefined
                Configuration.UI_MODE_NIGHT_UNDEFINED
                ->{
                    //Text Color
                    txtName.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                }
            }

            //Background
            categoryBackground.setBackgroundResource(R.drawable.category_edit_container_background)
        }

        //Category not Selected
        private fun setNotSelected(){
            //Check Night ot Day Mode
            when (itemView.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                //Night Mode
                Configuration.UI_MODE_NIGHT_YES
                ->{
                    //Text Color
                    txtName.setTextColor(ContextCompat.getColor(itemView.context, R.color.dark_grey))
                    //Background Color
                    categoryBackground.setBackgroundResource(R.color.dark_background)
                }

                //Day Mode
                Configuration.UI_MODE_NIGHT_NO
                ->{
                    //Text Color
                    txtName.setTextColor(ContextCompat.getColor(itemView.context, R.color.lite_text))
                    //Background Color
                    categoryBackground.setBackgroundResource(R.color.background)
                }

                //Undefined
                Configuration.UI_MODE_NIGHT_UNDEFINED
                ->{
                    //Text Color
                    txtName.setTextColor(ContextCompat.getColor(itemView.context, R.color.dark_grey))
                    //Background Color
                    categoryBackground.setBackgroundResource(R.color.dark_background)
                }
            }
        }
    }
}