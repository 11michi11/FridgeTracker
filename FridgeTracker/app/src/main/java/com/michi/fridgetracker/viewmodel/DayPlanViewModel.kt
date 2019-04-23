package com.michi.fridgetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.michi.fridgetracker.persistance.PlansRepository
import java.time.LocalDate

class DayPlanViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var plansRepository: PlansRepository

    fun init() {
        plansRepository = PlansRepository(this.getApplication())
    }

    fun getPlansMeals(date: LocalDate) = plansRepository.findForDate(date)

}