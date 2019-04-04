package com.michi.fridgetracker.persistance

import androidx.room.Embedded
import androidx.room.Relation
import com.michi.fridgetracker.domain.Ingredient
import com.michi.fridgetracker.domain.Meal

class MealWithIngredients(
    @Embedded
    var meal: Meal,

    @Relation(parentColumn = "mealId", entityColumn = "ingredientId", entity = Ingredient::class)
    var ingredients: List<Ingredient>
)