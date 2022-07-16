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
import com.example.straight_habits.activity.routine.ShowRoutineActivity
import com.example.straight_habits.adapters.categories.CategoriesAdapter
import com.example.straight_habits.beans.RoutineBean
import com.example.straight_habits.controller.application.ManageCategories
import com.example.straight_habits.controller.application.ManageRoutine
import com.example.straight_habits.database.RoomDB
import com.example.straight_habits.fragments.details.CategoryDetailsEditFragment
import com.example.straight_habits.interfaces.UpdateEditedListInterface
import com.example.straight_habits.interfaces.categories.CategoryDetailsInterface
import com.example.straight_habits.interfaces.categories.EditCategoryInterface
import com.example.straight_habits.interfaces.categories.SelectCategoryInterface
import com.example.straight_habits.models.CategoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ShowCategoriesFragment : Fragment(),
    SelectCategoryInterface, CategoryDetailsInterface, UpdateEditedListInterface, EditCategoryInterface {
    //Categories
    private lateinit var rvCategories: RecyclerView
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var categoriesList: MutableList<CategoryModel>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_categories, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Recycler View
        rvCategories = view.findViewById(R.id.rv_categories)

        //Using Coroutines to Manage the Room DB
        lifecycleScope.launch(Dispatchers.IO){
            //Get Categories List
            getCategoriesList()

            activity?.runOnUiThread {
                //Set the Recycler View
                setCategoriesRecyclerView()
            }
        }
    }





    //Categories
    //Get Categories
    private suspend fun getCategoriesList(){
        //Database Instance
        val DB = RoomDB.getInstance(requireContext()).categoryDAO()

        //Habits List
        categoriesList = ArrayList()
        categoriesList = DB.readAll()
    }

    //Set Recycler View
    private fun setCategoriesRecyclerView(){
        //Adapter
        categoriesAdapter = CategoriesAdapter(categoriesList, this, this)

        //Recycler View
        rvCategories
            .layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvCategories.adapter = categoriesAdapter
    }



    //Categories
    fun selectFragment(){
        //The use of coroutine is obligatory cause else the main thread would be stacked waiting the
        //initialization of the categories list
        lifecycleScope.launch {
            var counter = 0
            //Wait until the categoriesList is initialized
            while(!this@ShowCategoriesFragment::categoriesList.isInitialized)
                delay(10)
            while(categoriesList.size == 0 && counter != 10){
                delay(10)
                //Exit Conditions
                counter++
            }


            //Get the Selected Category
            //val position = ManageCategoriesFacade.getSelectedPosition(categoriesList)

            //Call the ShowRoutineActivity method to update the Fragment showed list
            if(categoriesList.size != 0)
                (activity as ShowRoutineActivity?)?.setHabitsFragment(categoriesList[0].getName())
        }
    }



    //Interface Methods-----------------------------------------------------------------------------
    //SelectCategoryInterface-----------------------------------------------------------------------
    override fun selectCategory(position: Int) {
        //Deselect All
        for(i in 0 until categoriesList.size)
            categoriesList[i].setSelected(false)
        //Select One
        categoriesList[position].setSelected(true)

        //Notify the Adapter
        categoriesAdapter.notifyDataSetChanged()

        //Call the ShowRoutineActivity method to update the Fragment showed list
        (activity as ShowRoutineActivity?)?.setHabitsFragment(categoriesList[position].getName())
    }

    //CategoryDetailsInterface----------------------------------------------------------------------
    override fun openCategoryEditDeletePopup(position: Int) {
        val bundle = Bundle()
        //Put the Routine to edit
        bundle.putSerializable("Edit Category Details", categoriesList[position])
        //Put the Position of the Routine to edit
        bundle.putInt("Edit Category Position", position)

        //Create the Details Fragment
        val categoryDetailsEditFragment = CategoryDetailsEditFragment(this, this)
        categoryDetailsEditFragment.arguments = bundle
        activity?.let{categoryDetailsEditFragment.show(it.supportFragmentManager,"EditCategoryDetailsFragment")}
    }


    //UpdateEditedListInterface---------------------------------------------------------------------
    override fun updateRoutineList(position: Int, routineModel: RoutineBean) {
        //
    }

    override fun updateCategoryList(position: Int, category: CategoryModel) {
        //Check if the Position of the edited Category is the one of the Selected One
        if(category.getSelected()){
            //Call the ShowRoutineActivity method to update the Fragment showed list
            (activity as ShowRoutineActivity?)?.setHabitsFragment(categoriesList[position].getName())
        }

        //Remove the old Category in the Position
        categoriesList.removeAt(position)

        //Add the new edited Category in the position
        categoriesList.add(position, category)

        //Update the Adapter
        categoriesAdapter.notifyDataSetChanged()
    }


    //EditCategoryInterface-------------------------------------------------------------------------
    override fun deleteCategory(position: Int) {
        //Application Controller
        val manageCategories = ManageCategories()
        val manageRoutine = ManageRoutine()

        //Coroutine
        runBlocking {
            //Delete the Routine of the Category
            manageRoutine.deleteRoutineListCategory(categoriesList[position].getName(), requireContext())
            //Delete the Category
            manageCategories.deleteCategory(categoriesList[position], requireContext())
        }

        //Remove the Category from the List
        categoriesList.removeAt(position)

        //Notify the Adapter
        categoriesAdapter.notifyItemRemoved(position)


        //Check if there's no category selected. If there's not, it means that the selected one has
        //been eliminated.
        for(category in categoriesList)
            //If there's one category selected break the loop and exit
            if(category.getSelected()) return

        if(position >= categoriesList.size && position != 0){
            categoriesList[position - 1].setSelected(true)
            categoriesAdapter.notifyDataSetChanged()

            //Call the ShowRoutineActivity method to update the Fragment showed list
            (activity as ShowRoutineActivity?)?.setHabitsFragment(categoriesList[position - 1].getName())
        }
        else{
            categoriesList[position].setSelected(true)
            categoriesAdapter.notifyDataSetChanged()

            //Call the ShowRoutineActivity method to update the Fragment showed list
            (activity as ShowRoutineActivity?)?.setHabitsFragment(categoriesList[position].getName())
        }

    }
}