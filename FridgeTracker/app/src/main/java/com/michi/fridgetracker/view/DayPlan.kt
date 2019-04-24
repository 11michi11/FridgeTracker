package com.michi.fridgetracker.view

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.michi.fridgetracker.R
import com.michi.fridgetracker.domain.Meal
import com.michi.fridgetracker.view.Calendar.Companion.DATE_KEY
import com.michi.fridgetracker.viewmodel.adapters.DayPlanAdapter
import com.michi.fridgetracker.viewmodel.DayPlanViewModel
import kotlinx.android.synthetic.main.day_plan_fragment.*
import java.time.LocalDate

class DayPlan : Fragment() {

    companion object {
        public const val CHOOSE_MEALS_REQUEST = 137
        public const val MEALS_KEY = "meals"
        fun newInstance() = DayPlan()
    }

    private lateinit var viewModel: DayPlanViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.day_plan_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DayPlanViewModel::class.java)

        planMeal.setOnClickListener { choseMeals(it) }

        val date = arguments?.getSerializable(DATE_KEY) as LocalDate
        meals.hasFixedSize()
        meals.layoutManager = LinearLayoutManager(meals.context)

        val adapter = DayPlanAdapter(viewModel)
        meals.adapter = adapter

        viewModel.getPlansMeals(date).observe(this, Observer { plan ->
            val meals = plan.meals.map { it.meal }
            adapter.setMeals(meals)
            viewModel.setPlan(plan)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CHOOSE_MEALS_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                @Suppress("UNCHECKED_CAST")
                val meals = data?.extras?.getSerializable(MEALS_KEY) as ArrayList<Meal>
                viewModel.addMeals(meals)
                Toast.makeText(this.context, "Choosen meals: $meals", Toast.LENGTH_SHORT).show()
                Log.d("Meals", meals.toString())
            }
        }
    }

    public fun choseMeals(view: View) {
        val intent = Intent(this.context, ChooseMealActivity::class.java)
        startActivityForResult(intent, CHOOSE_MEALS_REQUEST)
    }

}
