package com.michi.fridgetracker.domain

import java.time.LocalTime

class Meal(
    val name : String,
    val time : LocalTime,
    private val ingredients : List<Ingredient>) {

    fun getIgredients() = ingredients.toList()
}