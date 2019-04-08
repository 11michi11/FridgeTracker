package com.michi.fridgetracker.persistance

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.michi.fridgetracker.domain.Ingredient
import com.michi.fridgetracker.domain.IngredientToChoose

class IngredientsRepository(application: Application) {

    private var ingredientsDao: IngredientsDao
    private var ingredients: LiveData<List<Ingredient>>
    private var ingredientsMediator  = MediatorLiveData<List<IngredientToChoose>>()

    companion object {
        private var INSTANCE: IngredientsRepository? = null

        fun getInstance(application: Application): IngredientsRepository {
            if (INSTANCE == null) {
                synchronized(IngredientsRepository::class) {
                    INSTANCE = IngredientsRepository(application)
                }
            }
            return INSTANCE!!
        }
    }

    init {
        val db = FridgeRoomDatabase.getAppDataBase(application)
        ingredientsDao = db!!.ingredientsDao()
        ingredients = ingredientsDao.findAll()
    }

    public fun getAllIngredients() = ingredients

    public fun getAllIngredientsToChoose(): LiveData<List<IngredientToChoose>> {
        val all = ingredientsDao.findAll()

        ingredientsMediator.addSource(all) {
            if(it != null){
                ingredientsMediator.removeSource(all)
                ingredientsMediator.value = it.map { ingredient -> IngredientToChoose(ingredient) }
            }
        }

        return ingredientsMediator
    }

    public fun insert(ingredient: Ingredient) {
        InsertAsyncTask(ingredientsDao).execute(ingredient)
    }


    private class InsertAsyncTask(val ingredientsDao: IngredientsDao) : AsyncTask<Ingredient, Unit, Unit>() {

        override fun doInBackground(vararg ingredient: Ingredient?) {
            ingredientsDao.insert(ingredient[0]!!)
        }
    }
}