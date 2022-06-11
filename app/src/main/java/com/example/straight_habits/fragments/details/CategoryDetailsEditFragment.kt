package com.example.straight_habits.fragments.details

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.straight_habits.R
import com.example.straight_habits.beans.RoutineBean
import com.example.straight_habits.controller.application.ManageCategories
import com.example.straight_habits.controller.application.ManageRoutine
import com.example.straight_habits.facade.ManageDaysFacade
import com.example.straight_habits.facade.ManageRoutineFacade
import com.example.straight_habits.interfaces.UpdateEditedListInterface
import com.example.straight_habits.models.CategoryModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.runBlocking


class CategoryDetailsEditFragment(private val updateEditedListInterface: UpdateEditedListInterface) : DialogFragment() {
    //Button
    private lateinit var btnBack: ImageView
    private lateinit var btnDone: FloatingActionButton
    //Text
    private lateinit var txtName: EditText

    //Category
    private lateinit var category: CategoryModel
    private var position: Int = 0
    private var oldName: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Set transparent background and no title
        if (dialog != null && dialog!!.window != null) {
            dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dialog!!.window?.requestFeature(Window.FEATURE_NO_TITLE);
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_details_edit, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Back Button
        btnBack = view.findViewById(R.id.btn_habit_details_edit_back)
        btnBack.setOnClickListener {
            dismiss()
        }

        //Done Button
        btnDone = view.findViewById(R.id.btn_habit_details_edit_done)
        btnDone.setOnClickListener{
            //If the Category has been updated than print it and close the fragment
            if(editCategory(category)){
                //Edit the Routine Category
                val manageRoutine = ManageRoutine()
                runBlocking {
                    manageRoutine.editRoutineListCategory(category.getName(), oldName, requireContext())
                }


                Toast.makeText(requireContext(), "Habit Updated!", Toast.LENGTH_SHORT).show()

                updateEditedListInterface.updateCategoryList(position, category)
            }

            dismiss()
        }



        //Category's Details
        txtName = view.findViewById(R.id.txt_category_details_edit_name)

        //Get Bundle
        val bundle = arguments

        //Get Category
        category = bundle!!.getSerializable("Edit Category Details") as CategoryModel
        position = bundle!!.getInt("Edit Category Position")
        oldName = category.getName()


        setText(category)
    }



    private fun setText(category: CategoryModel){
        txtName.hint = category.getName()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun editCategory(category: CategoryModel): Boolean{
        //Application Controller
        val manageCategories = ManageCategories()

        //Get Text
        var name: String


        //Name
        if (txtName.editableText.toString() != ""){
            name = txtName.editableText.toString()
            category.setName(name)
        }


        //Update the DB
        runBlocking {
            manageCategories.editCategory(category, requireContext())
        }

        return true
    }
}