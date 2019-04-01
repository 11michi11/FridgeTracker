package com.michi.fridgetracker.persistance

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.michi.fridgetracker.domain.Ingredient

class IngredientsRepository(application: Application) {

    private var ingredientsDao: IngredientsDao
    private var ingredients : LiveData<List<Ingredient>>

    init {
        val db = FridgeRoomDatabase.getAppDataBase(application)
        ingredientsDao = db!!.ingredientsDao()
        ingredients = ingredientsDao.findAll()
    }

    public fun getAllIngredients() = ingredients

    public fun insert(ingredient: Ingredient){
        InsertAsyncTask(ingredientsDao).execute(ingredient)
    }


    private class InsertAsyncTask(val ingredientsDao: IngredientsDao) : AsyncTask<Ingredient, Unit, Unit>() {

        override fun doInBackground(vararg ingredient: Ingredient?) {
            ingredientsDao.insert(ingredient[0]!!)
        }
    }
}