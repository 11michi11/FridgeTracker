package com.michi.fridgetracker.domain

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable
import java.time.LocalTime

@Entity(tableName = "meals")
data class Meal(
    var name: String,
    @PrimaryKey(autoGenerate = true)
var mealId: Int = 0
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

@Entity(tableName = "plans_meals")
data class PlansMeal(
    @Embedded
    var meal: Meal,
    var planId: Int,
    @PrimaryKey(autoGenerate = true)
    var plansMealId: Int = 0
) : Serializable

