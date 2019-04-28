package com.michi.fridgetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.michi.fridgetracker.domain.Ingredient
import com.michi.fridgetracker.domain.Meal
import com.michi.fridgetracker.persistance.MealRepository

class AddMealViewModel(application: Application) : AndroidViewModel(application), IngredientSavableViewModel {
    private var mealRepository = MealRepository.getInstance(this.getApplication())

    private val ingredientsLiveData = MutableLiveData<List<Ingredient>>()
    private val ingredients = mutableListOf<Ingredient>()

    init {
        ingredientsLiveData.value = ingredients
    }

    fun getIngredients(): LiveData<List<Ingredient>> = ingredientsLiveData

    fun addIngredients(ingredients: List<Ingredient>) {
        this.ingredients.addAll(ingredients)
        ingredientsLiveData.value = this.ingredients
    }

    fun saveMeal(name: String) {
        val meal = Meal(name)
        meal.addIngredients(ingredients)

        mealRepository.insert(meal)
    }

    override fun saveIngredientQuantity(quantity: Double, position: Int) {
        ingredients[position].quantity = quantity
        ingredientsLiveData.value = ingredients
    }
}
