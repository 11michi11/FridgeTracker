package com.michi.fridgetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.michi.fridgetracker.domain.MealToChoose
import com.michi.fridgetracker.persistance.MealRepository

class ChooseMealsViewModel(application: Application): AndroidViewModel(application) {

    private lateinit var mealsRepo: MealRepository
    private lateinit var meals: LiveData<List<MealToChoose>>

    fun init() {
        mealsRepo= MealRepository.getInstance(this.getApplication())
        meals = mealsRepo.getAllIngredientsToChoose()
    }


    fun getAllMeals(): LiveData<List<MealToChoose>> = meals
}