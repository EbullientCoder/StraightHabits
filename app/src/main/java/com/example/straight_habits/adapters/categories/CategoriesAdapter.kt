package com.example.straight_habits.adapters.categories


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
import com.example.straight_habits.interfaces.categories.CategoryDetailsInterface
import com.example.straight_habits.interfaces.categories.SelectCategoryInterface
import com.example.straight_habits.models.CategoryModel
import kotlinx.coroutines.runBlocking






class CategoriesAdapter(
    private val categories: MutableList<CategoryModel>,
    private val selectCategoryInterface: SelectCategoryInterface,
    private val categoryDetailsInterface: CategoryDetailsInterface
): RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>()  {

    //Personalized View Holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.container_category, parent, false)

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
        //Habits Left
        private val txtHabitsLeft = itemView.findViewById<TextView>(R.id.txt_habits_left)
        //Habits Counter
        private val habitsCounter = itemView.findViewById<ConstraintLayout>(R.id.habits_left_background)
        private val container = itemView.findViewById<ConstraintLayout>(R.id.category_container)


        //Methods
        @RequiresApi(Build.VERSION_CODES.O)
        fun setCommonData(category: CategoryModel){
            //Select Category Click Listener
            container.setOnClickListener{
                selectCategoryInterface.selectCategory(adapterPosition)
            }
            //Open Category Edit/Delete Popup
            container.setOnLongClickListener {
                categoryDetailsInterface.openCategoryEditDeletePopup(adapterPosition)

                return@setOnLongClickListener true
            }

            //Set Name
            txtName.text = category.getName()

            //Set Selected
            if(category.getSelected()){
                //Set Text Color
                txtName.setTextColor(ContextCompat.getColor(itemView.context, R.color.blue))
                //Set Background
                categoryBackground.setBackgroundResource(R.drawable.category_container_background)
                //Habits Counter
                habitsCounter.visibility = View.GONE
            }
            else{
                //Set Background
                categoryBackground.setBackgroundResource(R.color.dark_background)
                //Set Text Color
                txtName.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))

                //Habits Counter
                habitsCounter.visibility = View.GONE
                runBlocking {
                    val num = getHabitsNumber(category)

                    if(num != 0){
                        habitsCounter.visibility = View.VISIBLE
                        txtHabitsLeft.text = num.toString()
                    }
                }
            }
        }



        //Get the Not Done Habits
        @RequiresApi(Build.VERSION_CODES.O)
        private suspend fun getHabitsNumber(category: CategoryModel): Int{
            //Database Instance
            val DB = RoomDB.getInstance(itemView.context)
            val dao = DB.routineDAO()

            //Get all the habits in the category
            val habits = dao.readAllCategory(ManageDaysFacade.getCurrentDay(), category.getName())
            var num = 0
            for(habit in habits)
                if(!habit.getDone()) num++

            return num
        }
    }
}