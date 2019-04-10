package com.michi.fridgetracker.persistance

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import com.michi.fridgetracker.domain.Meal
import com.michi.fridgetracker.domain.MealsIngredient

@Dao
abstract class MealsDao {

    fun insert(meal: Meal) {
        val mealId = insertOnlyMeal(meal).toInt()
        meal.ingredients.forEach {
            it.mealId = mealId
            insertMealsIngredient(it)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMealsIngredient(mealsIngredient: MealsIngredient)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOnlyMeal(meal: Meal): Long

    public fun findAll(): List<Meal> {
        val meals = findAllMeals()

        for (meal in meals) {
            val mealsWithIngredients = findAllMealsIngredients(meal.mealId)
            meal.ingredients = mealsWithIngredients.toMutableList()
        }
        return meals
    }

    @Query("select * from meals")
    abstract fun findAllMeals(): List<Meal>

    @Query("select * from meals_ingredients where mealId = :mealId")
    abstract fun findAllMealsIngredients(mealId: Int): List<MealsIngredient>

    @Query("select * from meals where mealId = :mealId")
    abstract fun findById(mealId: Int): LiveData<Meal>

    @Query("delete from meals")
    abstract fun deleteAll()

    fun delete(meal: Meal) {
        meal.ingredients.forEach {deleteMealsIngredient(it) }
        deleteMeal(meal.mealId)
    }


    @Query("delete from meals where mealId = :mealId")
    abstract fun deleteMeal(mealId: Int)

    @Delete
    abstract fun deleteMealsIngredient(mealsIngredient: MealsIngredient)


}