package com.michi.fridgetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.michi.fridgetracker.domain.IngredientToChoose
import com.michi.fridgetracker.persistance.IngredientsRepository

class ShoppingListViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var ingredientRepo: IngredientsRepository
    private lateinit var ingredients: LiveData<List<IngredientToChoose>>

    fun init() {
        ingredientRepo = IngredientsRepository.getInstance(this.getApplication())
        ingredients = ingredientRepo.getAllShoppingIngredients()
    }


    fun getAllIngredients(): LiveData<List<IngredientToChoose>> = ingredients

    fun addIngredientQuantity(ingredientId: Int, quantity: Double) {
        ingredientRepo.addIngredientQuantity(ingredientId, quantity)
    }


}