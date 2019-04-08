package com.michi.fridgetracker.persistance

import androidx.lifecycle.LiveData
import androidx.room.*
import com.michi.fridgetracker.domain.Ingredient

@Dao
interface IngredientsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ingredient: Ingredient)

    @Delete
    fun delete(ingredient: Ingredient)

    @Update
    fun update(ingredient: Ingredient)

    @Query("select * from ingredients")
    fun findAll() : LiveData<List<Ingredient>>

    @Query("select * from ingredients")
    fun getAll() : List<Ingredient>

    @Query("SELECT * from ingredients where ingredientId = :ingredientId")
    fun findById(ingredientId : Int) : Ingredient

    @Query("delete from ingredients")
    fun deleteAll()

}