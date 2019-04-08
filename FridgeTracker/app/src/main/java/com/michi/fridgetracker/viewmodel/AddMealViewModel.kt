package com.michi.fridgetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.michi.fridgetracker.domain.Ingredient

class AddMealViewModel(application: Application) : AndroidViewModel(application) {

    private val ingredientsLiveData = MutableLiveData<List<Ingredient>>()
    private val ingredients = mutableListOf<Ingredient>()

    init {
        ingredients.add(Ingredient(name = "Test", quantity = 1.0, price = 2.0))
        ingredientsLiveData.value = ingredients
    }

    fun getIngredients(): LiveData<List<Ingredient>> = ingredientsLiveData

    fun addIngredients(ingredients: List<Ingredient>) {
        this.ingredients.addAll(ingredients)
        ingredientsLiveData.value = this.ingredients
    }
}
