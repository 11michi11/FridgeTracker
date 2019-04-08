package com.michi.fridgetracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.michi.fridgetracker.domain.Meal

class MenuViewModel : ViewModel() {

    private lateinit var adapter: MenuAdapter
    private lateinit var meals: MutableLiveData<List<Meal>>

    fun init() {
        meals = MutableLiveData()
        meals.value = listOf(Meal(name = "Salami Sandwich"))
    }

    public fun insert(meal: Meal) {

    }

    fun getAllMeals(): LiveData<List<Meal>> = meals
}
