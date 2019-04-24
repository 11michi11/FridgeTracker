package com.michi.fridgetracker.persistance

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.MutableLiveData
import com.michi.fridgetracker.domain.DayPlan
import com.michi.fridgetracker.domain.Meal
import com.michi.fridgetracker.domain.PlansMeal
import java.time.LocalDate

class PlansRepository(application: Application) {

    private var plansDao: PlansDao
    private var plans: MutableLiveData<List<DayPlan>> = MutableLiveData()

    companion object {
        private var INSTANCE: PlansRepository? = null

        fun getInstance(application: Application): PlansRepository {
            if (INSTANCE == null) {
                synchronized(PlansRepository::class) {
                    INSTANCE = PlansRepository(application)
                }
            }
            return INSTANCE!!
        }
    }

    init {
        val db = FridgeRoomDatabase.getAppDataBase(application)
        plansDao = db!!.plansDao()
        FindAllAsyncTask(plansDao).execute(plans)
    }

    public fun getAllPlans(): MutableLiveData<List<DayPlan>> {
        FindAllAsyncTask(plansDao).execute(plans)
        return plans
    }

    public fun insert(plan: DayPlan) {
        InsertAsyncTask(plansDao).execute(plan)
    }

    public fun delete(plan: DayPlan) {
        DeleteAsyncTask(plansDao).execute(plan)
    }

    fun findForDate(date: LocalDate): MutableLiveData<DayPlan> {
        val liveData = MutableLiveData<DayPlan>()
        FindByDateAsyncTask(plansDao, liveData).execute(date)
        return liveData
    }

    fun deleteMealFromPlan(dayPlanId: Int) {
        DeleteMealFromPlanAsyncTask(plansDao).execute(dayPlanId)
    }

    fun addMealsToPlan(meals: List<Meal>, dayPlanId: Int) {
        InsertMealsToPlanAsyncTask(plansDao).execute(MealsWithPlanId(meals, dayPlanId))
    }

    private class FindByDateAsyncTask(val plansDao: PlansDao,val liveData: MutableLiveData<DayPlan>) : AsyncTask<LocalDate, Unit, Unit>() {

        override fun doInBackground(vararg date: LocalDate) {
            val plan = plansDao.findByDate(date[0])
            liveData.postValue(plan)
        }
    }

    private class DeleteMealFromPlanAsyncTask(val plansDao: PlansDao) : AsyncTask<Int, Unit, Unit>() {

        override fun doInBackground(vararg data: Int?) {
            plansDao.deleteMealFromPlan(data[0]!!)
        }
    }

    private class DeleteAsyncTask(val plansDao: PlansDao) : AsyncTask<DayPlan, Unit, Unit>() {

        override fun doInBackground(vararg plans: DayPlan) {
            plansDao.delete(plans[0])
        }
    }

    private class FindAllAsyncTask(val plansDao: PlansDao) : AsyncTask<MutableLiveData<List<DayPlan>>, Unit, Unit>() {

        override fun doInBackground(vararg meals: MutableLiveData<List<DayPlan>>) {
            meals[0].postValue(plansDao.findAll())
        }
    }

    private class InsertAsyncTask(val plansDao: PlansDao) : AsyncTask<DayPlan, Unit, Unit>() {

        override fun doInBackground(vararg plan: DayPlan?) {
            plansDao.insert(plan[0]!!)
        }
    }

    private class InsertMealsToPlanAsyncTask(val plansDao: PlansDao) : AsyncTask<MealsWithPlanId, Unit, Unit>() {

        override fun doInBackground(vararg data: MealsWithPlanId) {
            val (list, planId) = data[0]
            list.forEach {
                plansDao.insertPlansMeals(PlansMeal(it, planId = planId))
            }
            val findByDate = plansDao.findById(planId)

        }
    }
}

data class MealsWithPlanId(val meals: List<Meal>, val dayPlanId: Int)