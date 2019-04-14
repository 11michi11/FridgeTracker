package com.michi.fridgetracker.persistance

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.michi.fridgetracker.domain.Meal

class MealRepository(application: Application) {

    private var mealsDao: MealsDao
    private var meals: MutableLiveData<List<Meal>> = MutableLiveData()

    companion object {
        private var INSTANCE: MealRepository? = null

        fun getInstance(application: Application): MealRepository {
            if (INSTANCE == null) {
                synchronized(MealRepository::class) {
                    INSTANCE = MealRepository(application)
                }
            }
            return INSTANCE!!
        }
    }

    init {
        val db = FridgeRoomDatabase.getAppDataBase(application)
        mealsDao = db!!.mealsDao()
        FindAllAsyncTask(mealsDao).execute(meals)
    }

    public fun getAllMeals(): MutableLiveData<List<Meal>> {
        FindAllAsyncTask(mealsDao).execute(meals)
        return meals
    }

    public fun insert(meal: Meal) {
        InsertAsyncTask(mealsDao).execute(meal)
    }

    public fun delete(meal: Meal){
        DeleteAsyncTask(mealsDao).execute(meal)
    }

    private class DeleteAsyncTask(val mealsDao: MealsDao) : AsyncTask<Meal, Unit, Unit>() {

        override fun doInBackground(vararg meals: Meal) {
            mealsDao.delete(meals[0])
        }
    }

    private class FindAllAsyncTask(val mealsDao: MealsDao) : AsyncTask<MutableLiveData<List<Meal>>, Unit, Unit>() {

        override fun doInBackground(vararg meals: MutableLiveData<List<Meal>>) {
            meals[0].postValue(mealsDao.findAll())
        }
    }

    private class InsertAsyncTask(val mealsDao: MealsDao) : AsyncTask<Meal, Unit, Unit>() {

        override fun doInBackground(vararg meal: Meal?) {
            mealsDao.insert(meal[0]!!)
        }
    }
}