package com.michi.fridgetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.michi.fridgetracker.domain.Meal
import com.michi.fridgetracker.persistance.MealRepository

class MenuViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var mealRepository: MealRepository
    private lateinit var meals: MutableLiveData<List<Meal>>

    fun init() {
        mealRepository = MealRepository.getInstance(this.getApplication())
        meals = mealRepository.getAllMeals()
    }

    public fun insert(meal: Meal) {
        mealRepository.insert(meal)
    }

    fun getAllMeals(): LiveData<List<Meal>> = meals

    fun delete(meal: Meal?) {
        if (meal != null) {
            val list = meals.value?.toMutableList()!!
            list.remove(meal)
            meals.value = list.toList()
            mealRepository.delete(meal)
        }
    }
}
