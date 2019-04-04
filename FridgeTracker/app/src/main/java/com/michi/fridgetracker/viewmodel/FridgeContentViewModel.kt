package com.michi.fridgetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.michi.fridgetracker.domain.Ingredient
import com.michi.fridgetracker.persistance.IngredientsRepository
import com.michi.fridgetracker.view.IngredientsAdapter

class FridgeContentViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var adapter: IngredientsAdapter
    private lateinit var ingredientRepo: IngredientsRepository
    private lateinit var ingredients: LiveData<List<Ingredient>>

    fun init() {
        ingredientRepo = IngredientsRepository(this.getApplication())
        ingredients = ingredientRepo.getAllIngredients()
    }

    public fun insert(ingredient: Ingredient) = ingredientRepo.insert(ingredient)

    fun getAllIngredients(): LiveData<List<Ingredient>> = ingredients


}