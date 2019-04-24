package com.michi.fridgetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.michi.fridgetracker.domain.DayPlan
import com.michi.fridgetracker.domain.Meal
import com.michi.fridgetracker.persistance.PlansRepository
import java.time.LocalDate

class DayPlanViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var plansRepository: PlansRepository
    private lateinit var planLiveData: MutableLiveData<DayPlan>
    private lateinit var plan: DayPlan

    init {
        plansRepository = PlansRepository(this.getApplication())
    }

    fun getPlansMeals(date: LocalDate): MutableLiveData<DayPlan> {
        planLiveData = plansRepository.findForDate(date)
        return planLiveData
    }

    fun deleteMeal(position: Int) {
        plan.deleteMeal(position)
        plansRepository.deleteMealFromPlan(plan.dayPlanId)
        planLiveData.value = plan
    }

    fun setPlan(plan: DayPlan){
        this.plan = plan
    }

    fun addMeals(meals: List<Meal>) {
        plan.addAll(meals)
        planLiveData.value = plan
        plansRepository.insert(plan)
//        plansRepository.addMealsToPlan(meals, plan.dayPlanId)
    }

}