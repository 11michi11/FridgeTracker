package com.michi.fridgetracker.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.michi.fridgetracker.R
import com.michi.fridgetracker.domain.Meal
import com.michi.fridgetracker.view.DayPlan.Companion.MEALS_KEY
import com.michi.fridgetracker.viewmodel.ChooseIngredientViewModel
import com.michi.fridgetracker.viewmodel.ChooseMealsViewModel
import com.michi.fridgetracker.viewmodel.adapters.MealsChoiceAdapter
import kotlinx.android.synthetic.main.activity_choose_meal.*
import kotlinx.android.synthetic.main.day_plan_fragment.*

class ChooseMealActivity : AppCompatActivity() {

    private lateinit var viewModel: ChooseMealsViewModel
    private lateinit var adapter: MealsChoiceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_meal)

        viewModel = ViewModelProviders.of(this).get(ChooseMealsViewModel::class.java)

        if (savedInstanceState == null) {
            viewModel.init()
        }

        mealsChoice.hasFixedSize()
        mealsChoice.layoutManager = LinearLayoutManager(mealsChoice.context)
        adapter = MealsChoiceAdapter()
        mealsChoice.adapter = adapter
        viewModel.getAllMeals().observe(this, Observer { adapter.setMeals(it) })

    }


    fun addMeals(view: View) {
        val meals = adapter.getMeals().filter { it.isChecked }.map { it.meal }
        Log.d("Meals", meals.toString())
        val returnIntent = Intent()
        returnIntent.putExtra(MEALS_KEY, ArrayList<Meal>(meals))
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

}
