package com.michi.fridgetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.michi.fridgetracker.domain.Ingredient
import com.michi.fridgetracker.persistance.IngredientsRepository

class FridgeContentViewModel(application: Application) : AndroidViewModel(application), IngredientSavableViewModel {

    private lateinit var ingredientRepo: IngredientsRepository

    private lateinit var ingredients: LiveData<List<Ingredient>>
    fun init() {
        ingredientRepo = IngredientsRepository.getInstance(this.getApplication())
        ingredients = ingredientRepo.getAllIngredients()
    }

    public fun insert(ingredient: Ingredient) = ingredientRepo.insert(ingredient)

    fun getAllIngredients(): LiveData<List<Ingredient>> = ingredients

    override fun saveIngredientQuantity(quantity: Double, position: Int) {
        val ingredient = ingredients.value?.get(position)
        ingredient?.quantity = quantity
        ingredientRepo.insert(ingredient!!)
    }


}
