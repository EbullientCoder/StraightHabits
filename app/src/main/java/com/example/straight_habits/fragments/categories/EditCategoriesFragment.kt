package com.example.straight_habits.fragments.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.straight_habits.R
import com.example.straight_habits.activity.MainActivity
import com.example.straight_habits.adapters.categories.CategoriesAdapter
import com.example.straight_habits.adapters.categories.EditCategoriesAdapter
import com.example.straight_habits.database.RoomDB
import com.example.straight_habits.interfaces.SelectCategoryInterface
import com.example.straight_habits.models.CategoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EditCategoriesFragment : Fragment(), SelectCategoryInterface {
    //Categories
    private lateinit var rvCategories: RecyclerView
    private lateinit var categoriesAdapter: EditCategoriesAdapter
    private lateinit var categoriesList: MutableList<CategoryModel>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_categories, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Recycler View
        rvCategories = view.findViewById(R.id.rv_edit_categories)

        //Using Coroutines to Manage the Room DB
        lifecycleScope.launch(Dispatchers.IO){
            //Get Categories List
            getCategoriesList()

            //Set the Recycler View
            setCategoriesRecyclerView()
        }
    }



    //Get Categories
    private suspend fun getCategoriesList(){
        //Database Instance
        val DB = RoomDB.getInstance(requireContext()).categoryDAO()

        //Habits List
        categoriesList = DB.readAll()
    }

    //Set Recycler View
    private fun setCategoriesRecyclerView(){
        //Adapter
        categoriesAdapter = EditCategoriesAdapter(categoriesList, this)

        //Recycler View
        rvCategories
            .layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvCategories.adapter = categoriesAdapter
    }




    //Interface Methods-----------------------------------------------------------------------------
    override fun selectCategory(position: Int) {
        //Deselect All
        for(i in 0 until categoriesList.size)
            categoriesList[i].setSelected(false)
        //Select One
        categoriesList[position].setSelected(true)

        //Notify the Adapter
        categoriesAdapter.notifyDataSetChanged()

        //Call the MainActivity method to update the Fragment showed list
        (activity as MainActivity?)?.setHabitsFragment(categoriesList[position].getName(), true)
    }
}