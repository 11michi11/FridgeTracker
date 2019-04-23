package com.michi.fridgetracker.persistance

import androidx.lifecycle.LiveData
import androidx.room.*
import com.michi.fridgetracker.domain.DayPlan
import com.michi.fridgetracker.domain.PlansMeal
import java.time.LocalDate

@Dao
abstract class PlansDao {


    fun insert(plan: DayPlan) {
        val planId = insertOnlyPlan(plan).toInt()
        plan.meals.forEach {
            it.planId = planId
            insertPlansMeals(it)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertPlansMeals(plansMeal: PlansMeal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOnlyPlan(plan: DayPlan): Long

    public fun findAll(): List<DayPlan> {
        val plans = findAllPlans()

        for (plan in plans) {
            plan.meals = findAllPlansMeals(plan.dayPlanId).toMutableList()
        }
        return plans
    }

    @Query("select * from day_plans")
    abstract fun findAllPlans(): List<DayPlan>

    @Query("select * from plans_meals where planId = :planId")
    abstract fun findAllPlansMeals(planId: Int): List<PlansMeal>

    @Query("select * from day_plans where dayPlanId = :dayPlanId")
    abstract fun findById(dayPlanId: Int): LiveData<DayPlan>

    @Query("delete from day_plans")
    abstract fun deleteAll()

    fun delete(plan: DayPlan) {
        plan.meals.forEach {deletePlansMeal(it) }
        deleteMeal(plan.dayPlanId)
    }


    @Query("delete from day_plans where dayPlanId = :dayPlanId")
    abstract fun deleteMeal(dayPlanId: Int)

    @Delete
    abstract fun deletePlansMeal(plansMeal: PlansMeal)

    @Query("select * from day_plans where date = :date")
    abstract fun findByDate(date: LocalDate): DayPlan?


}