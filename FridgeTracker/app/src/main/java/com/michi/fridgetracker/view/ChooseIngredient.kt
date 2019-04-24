package com.michi.fridgetracker.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.michi.fridgetracker.R
import com.michi.fridgetracker.domain.Ingredient
import com.michi.fridgetracker.viewmodel.ChooseIngredientViewModel
import com.michi.fridgetracker.viewmodel.adapters.IngredientsChoiceAdapter
import kotlinx.android.synthetic.main.activity_choose_ingredient.*

class ChooseIngredient : AppCompatActivity() {

    private lateinit var viewModel: ChooseIngredientViewModel
    private lateinit var adapter: IngredientsChoiceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_ingredient)

        viewModel = ViewModelProviders.of(this).get(ChooseIngredientViewModel::class.java)

        if (savedInstanceState == null) {
            viewModel.init()
        }

        ingredientsChoice.hasFixedSize()
        ingredientsChoice.layoutManager = LinearLayoutManager(ingredientsChoice.context)
        adapter = IngredientsChoiceAdapter()
        ingredientsChoice.adapter = adapter
        viewModel.getAllIngredients().observe(this, Observer { adapter.setIngredients(it) })

    }


    public fun choseIngredients(view: View) {
        val ingredients = adapter.getIngredients().filter { it.isChecked }.map { it.ingredient }
        Log.d("Ingredients", ingredients.toString())
        val returnIntent = Intent()
        returnIntent.putExtra(AddMeal.INGREDIENTS_KEY, ArrayList<Ingredient>(ingredients))
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

}
