package com.michi.fridgetracker.domain

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "day_plans")
data class DayPlan(
    val date: LocalDate,
    @PrimaryKey(autoGenerate = true)
    val dayPlanId: Int = 0
) {

    @Ignore
    var meals: MutableList<PlansMeal> = mutableListOf()

    fun hasMeals() = meals.isNotEmpty()

    infix fun addMeal(meal: Meal){
        meals.add(PlansMeal(meal, dayPlanId))
    }

    override fun toString(): String {
        return "DayPlan(date=$date, dayPlanId=$dayPlanId, meals=$meals)"
    }


}


