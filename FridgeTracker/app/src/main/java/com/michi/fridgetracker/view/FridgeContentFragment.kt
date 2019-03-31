package com.michi.fridgetracker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.michi.fridgetracker.R
import com.michi.fridgetracker.domain.Ingredient
import kotlinx.android.synthetic.main.fragment_fridge_content.*

class FridgeContentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fridge_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addIngredient.setImageDrawable(
            ContextCompat.getDrawable(
                this.activity!!,
                R.drawable.ic_add
            )
        )
        addIngredient.setOnClickListener (Navigation.createNavigateOnClickListener(R.id.action_fridgeContentFragment_to_addIngredient2, null))
        ingredientsList.hasFixedSize()
        ingredientsList.layoutManager = LinearLayoutManager(this.activity!!)
        ingredientsList.adapter = IngredientsAdapter(initIngredients())
    }

    private fun initIngredients(): List<Ingredient> = listOf(
        Ingredient("Spaghetti chili sauce", 1.0, 15.00),
        Ingredient("Salami Pork & Venison", 500.0, 30.00),
        Ingredient("Frozen bread rolls", 12.0, 11.00),
        Ingredient("Greek style coconut yogurt", 2.0, 8.00),
        Ingredient("Spaghetti chili sauce", 1.0, 15.00),
        Ingredient("Spaghetti chili sauce", 1.0, 15.00),
        Ingredient("Spaghetti chili sauce", 1.0, 15.00)
    )

}
