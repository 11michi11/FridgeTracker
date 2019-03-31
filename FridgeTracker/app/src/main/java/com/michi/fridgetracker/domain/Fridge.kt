package com.michi.fridgetracker.domain

class Fridge {

    private val ingredients = mutableMapOf<Ingredient, Double>()

    fun addIngredient(ingredient: Ingredient) {
        if (ingredients[ingredient] == null)
            ingredients[ingredient]?.inc()
        else
            ingredients[ingredient] = 1.0
    }

    fun getIngredients() = ingredients.entries

}