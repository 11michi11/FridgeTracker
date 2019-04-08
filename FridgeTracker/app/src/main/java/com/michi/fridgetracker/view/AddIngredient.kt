package com.michi.fridgetracker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.michi.fridgetracker.R
import com.michi.fridgetracker.domain.Ingredient
import com.michi.fridgetracker.viewmodel.AddIngredientViewModel
import kotlinx.android.synthetic.main.fragment_add_ingredient.*
import java.lang.NumberFormatException

class AddIngredient : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_ingredient, container, false)
    }

    private lateinit var viewModel: AddIngredientViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(AddIngredientViewModel::class.java)
        if (savedInstanceState == null) {
            viewModel.init()
        }

        saveIngredient.setOnClickListener { saveIngredientAndGoBack(it) }

    }


    private fun saveIngredientAndGoBack(view: View) {
        val name = ingredientNameChoice.text.toString()
        val priceString = ingredientPrice.text.toString()
        val price =
            if (priceString.isBlank())
                0.0
            else
                try {
                    priceString.toDouble()
                } catch (e: NumberFormatException) {
                    0.0
                }

        viewModel.insert(Ingredient(name = name, price = price))
        Navigation.findNavController(view).navigateUp()
    }


}
