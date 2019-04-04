package com.michi.fridgetracker.domain

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.time.LocalTime

@Entity(tableName = "meals")
data class Meal(
    @PrimaryKey(autoGenerate = true)
    var mealId: Int = 0,
    var name: String
) {

    @Ignore
    var ingredients: List<Ingredient> = emptyList()

    fun hasIngredients() = ingredients.isNotEmpty()

}

