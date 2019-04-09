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
    var ingredients: MutableList<MealsIngredient> = mutableListOf()

    fun hasIngredients() = ingredients.isNotEmpty()

    fun addIngredients(ingredients: List<Ingredient>) {
        ingredients.forEach {
            this.ingredients.add(MealsIngredient(it, mealId))
        }
    }

    override fun toString(): String {
        return "Meal(mealId=$mealId, name='$name', ingredients=$ingredients)"
    }


}

