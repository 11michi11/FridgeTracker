package com.michi.fridgetracker.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.navigation.Navigation

import com.michi.fridgetracker.R
import kotlinx.android.synthetic.main.calendar_fragment.*
import java.time.LocalDate

class Calendar : Fragment() {

    companion object {
        const val DATE_KEY = "date"
        fun newInstance() = Calendar()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.calendar_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        planDayBtn.setOnClickListener { planADay(it) }
    }

    private fun planADay(view: View) {
        val date = LocalDate.of(datePicker.year, datePicker.month + 1, datePicker.dayOfMonth)
        val bundle = Bundle()
        bundle.putSerializable(DATE_KEY, date)
        Navigation.findNavController(view).navigate(R.id.dayPlan, bundle)
    }

}
