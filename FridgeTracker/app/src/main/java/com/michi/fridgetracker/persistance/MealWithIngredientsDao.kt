package com.michi.fridgetracker.persistance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.michi.fridgetracker.domain.Ingredient

@Dao
interface MealWithIngredientsDao {

//    @Insert
//    fun insert(mealWithIngredients: MealWithIngredients)
//
//    @Transaction
//    @Query("select * from meals where mealId = :mealId")
//    fun findAllIngredientsByMealId(mealId: Int) : List<Ingredient>
//
//    @Query("delete from meals")
//    fun deleteAll()

}