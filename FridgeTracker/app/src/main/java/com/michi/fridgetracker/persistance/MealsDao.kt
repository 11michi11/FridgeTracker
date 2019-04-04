package com.michi.fridgetracker.persistance

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.michi.fridgetracker.domain.Meal

@Dao
interface MealsDao {

    @Insert
    fun insert(meal: Meal)

    @Query("select * from meals")
    fun findAll() : LiveData<List<Meal>>

    @Query("select * from meals where mealId = :mealId")
    fun findById(mealId : Int) : LiveData<Meal>

    @Query("delete from meals")
    fun deleteAll()

}