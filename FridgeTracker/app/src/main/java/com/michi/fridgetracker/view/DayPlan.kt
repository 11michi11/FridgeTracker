package com.michi.fridgetracker.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.michi.fridgetracker.R
import com.michi.fridgetracker.view.Calendar.Companion.DATE_KEY
import com.michi.fridgetracker.viewmodel.DayPlanAdapter
import com.michi.fridgetracker.viewmodel.DayPlanViewModel
import com.michi.fridgetracker.viewmodel.IngredientsAdapter
import kotlinx.android.synthetic.main.day_plan_fragment.*
import kotlinx.android.synthetic.main.fragment_fridge_content.*
import java.time.LocalDate

class DayPlan : Fragment() {

    companion object {
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


        val date = arguments?.getSerializable(DATE_KEY) as LocalDate
        meals.hasFixedSize()
        meals.layoutManager = LinearLayoutManager(meals.context)

        val adapter = DayPlanAdapter(viewModel)
        meals.adapter = adapter

        viewModel.getPlansMeals(date).observe(this, Observer {
            val plan = it ?: com.michi.fridgetracker.domain.DayPlan(date)
            val meals = plan.meals.map { it.meal }
            adapter.setMeals(meals)
        })

    }
}
