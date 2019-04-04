package com.michi.fridgetracker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.michi.fridgetracker.R
import com.michi.fridgetracker.viewmodel.MenuViewModel
import kotlinx.android.synthetic.main.menu_fragment.*

class Menu : Fragment() {

    private lateinit var viewModel: MenuViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.menu_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MenuViewModel::class.java)

        if (savedInstanceState == null) {
            viewModel.init()
        }

        addMeal.setImageDrawable(
            ContextCompat.getDrawable(
                this.activity!!,
                R.drawable.ic_add
            )
        )
//        addIngredient.setOnClickListener(
//            Navigation.createNavigateOnClickListener(
//                R.id.action_fridgeContentFragment_to_addIngredient2,
//                null
//            )
//        )

        menu.hasFixedSize()
        menu.layoutManager = LinearLayoutManager(menu.context)
        val adapter = MenuAdapter()
        menu.adapter = adapter
        viewModel.getAllMeals().observe(this, Observer { adapter.setMeals(it) })

    }

}
