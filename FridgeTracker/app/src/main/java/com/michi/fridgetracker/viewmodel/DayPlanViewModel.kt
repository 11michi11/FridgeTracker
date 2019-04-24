package com.michi.fridgetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.michi.fridgetracker.domain.DayPlan
import com.michi.fridgetracker.persistance.PlansRepository
import java.time.LocalDate

class DayPlanViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var plansRepository: PlansRepository
    private lateinit var plan: DayPlan

    init {
        plansRepository = PlansRepository(this.getApplication())
    }

    fun getPlansMeals(date: LocalDate) = plansRepository.findForDate(date)

    fun setPlan(plan: DayPlan) {
        this.plan = plan
    }

    fun deleteMeal(position: Int) {
        plan.deleteMeal(position)
        plansRepository.deleteMealFromPlan(plan.dayPlanId)
    }

}