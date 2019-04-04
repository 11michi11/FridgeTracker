package com.michi.fridgetracker.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients")
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    var ingredientId: Int = 0,
    var name: String,
    var quantity: Double = 1.0,
    var price: Double?
)