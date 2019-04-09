package com.michi.fridgetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.michi.fridgetracker.domain.Meal
import com.michi.fridgetracker.persistance.MealRepository

class MenuViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var mealRepository: MealRepository
    private lateinit var meals: LiveData<List<Meal>>

    fun init() {
        mealRepository = MealRepository.getInstance(this.getApplication())
        meals = mealRepository.getAllMeals()
    }

    public fun insert(meal: Meal) {
        mealRepository.insert(meal)
    }

    fun getAllMeals(): LiveData<List<Meal>> = meals
}
