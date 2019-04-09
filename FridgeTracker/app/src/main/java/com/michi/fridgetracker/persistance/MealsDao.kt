package com.michi.fridgetracker.persistance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
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

    @Insert
    abstract fun insertMealsIngredient(mealsIngredient: MealsIngredient)

    @Insert
    abstract fun insertOnlyMeal(meal: Meal) : Long

    public fun findAll(): List<Meal> {
        val meals = findAllMeals()

        for (meal in meals) {
            val mealsWithIngredients = findAllMealsIngredients(meal.mealId)
            meal.ingredients = mealsWithIngredients.toMutableList()
        }
        return meals
    }

    @Query("select * from meals")
    abstract fun findAllMeals() : List<Meal>

    @Query("select * from meals_ingredients where mealId = :mealId")
    abstract fun findAllMealsIngredients(mealId : Int): List<MealsIngredient>

    @Query("select * from meals where mealId = :mealId")
    abstract fun findById(mealId: Int): LiveData<Meal>

    @Query("delete from meals")
    abstract fun deleteAll()

}