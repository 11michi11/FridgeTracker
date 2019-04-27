package com.michi.fridgetracker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.michi.fridgetracker.R
import com.michi.fridgetracker.viewmodel.DayPlanViewModel
import com.michi.fridgetracker.viewmodel.adapters.DayPlanAdapter
import kotlinx.android.synthetic.main.home_fragment.*
import java.time.LocalDate

class Home : Fragment() {

    companion object {
        fun newInstance() = Home()
    }

    private lateinit var viewModel: DayPlanViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DayPlanViewModel::class.java)

        val date = LocalDate.now()
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




}
