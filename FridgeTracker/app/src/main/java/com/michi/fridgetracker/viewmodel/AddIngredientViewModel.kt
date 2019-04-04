package com.michi.fridgetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.michi.fridgetracker.domain.Ingredient
import com.michi.fridgetracker.persistance.IngredientsRepository

class AddIngredientViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var ingredientRepo: IngredientsRepository

    fun init() {
        ingredientRepo = IngredientsRepository(this.getApplication())
    }

    public fun insert(ingredient: Ingredient) = ingredientRepo.insert(ingredient)

}