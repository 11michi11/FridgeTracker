package com.michi.fridgetracker.view

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.michi.fridgetracker.R
import com.michi.fridgetracker.domain.Ingredient
import com.michi.fridgetracker.viewmodel.AddMealViewModel
import com.michi.fridgetracker.viewmodel.IngredientsAdapter
import kotlinx.android.synthetic.main.add_meal_fragment.*


class AddMeal : Fragment() {

    companion object {
        public const val CHOOSE_INGREDIENT_REQUEST = 42
        public const val INGREDIENTS_KEY = "ingredients"
        fun newInstance() = AddMeal()
    }

    private lateinit var viewModel: AddMealViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_meal_fragment, container, false)
    }

    private lateinit var adapter: IngredientsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddMealViewModel::class.java)
        choseIngredients.setOnClickListener { choseIngredients(it) }
        saveMeal.setOnClickListener { saveMeal(it) }

        mealsIngredients.hasFixedSize()
        mealsIngredients.layoutManager = LinearLayoutManager(mealsIngredients.context)
        adapter = IngredientsAdapter()
        mealsIngredients.adapter = adapter
        viewModel.getIngredients().observe(this, Observer { adapter.setIngredients(it) })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CHOOSE_INGREDIENT_REQUEST) {
            if (resultCode == RESULT_OK) {
                @Suppress("UNCHECKED_CAST")
                val ingredients = data?.extras?.getSerializable(INGREDIENTS_KEY) as ArrayList<Ingredient>
                viewModel.addIngredients(ingredients)
                Toast.makeText(this.context, "Choosen ingredients: $ingredients", Toast.LENGTH_SHORT).show()
                Log.d("Ingredients", ingredients.toString())
            }
        }
    }

    public fun choseIngredients(view: View) {
        val intent = Intent(this.context, ChooseIngredient::class.java)
        startActivityForResult(intent, CHOOSE_INGREDIENT_REQUEST)
    }

    public fun saveMeal(view: View) {
        viewModel.saveMeal(mealNameCreate.text.toString())

        this.findNavController().navigate(R.id.menuFragment)
    }

}
