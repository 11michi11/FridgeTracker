package com.michi.fridgetracker.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.michi.fridgetracker.R
import com.michi.fridgetracker.view.Calendar.Companion.DATE_KEY
import com.michi.fridgetracker.viewmodel.DayPlanViewModel
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
        // TODO: Use the ViewModel

        val date = arguments?.getSerializable(DATE_KEY) as LocalDate


        viewModel.getPlansMeals(date).observe(this, Observer {
            var plan = it ?: com.michi.fridgetracker.domain.DayPlan(date)
            // Load recycler view adapter
        })

    }





}
