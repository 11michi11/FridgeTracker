package com.michi.fridgetracker.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients")
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var quantity: Double,
    var price: Double?
)