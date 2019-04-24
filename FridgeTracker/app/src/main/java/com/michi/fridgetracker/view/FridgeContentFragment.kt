package com.michi.fridgetracker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.michi.fridgetracker.R
import com.michi.fridgetracker.viewmodel.FridgeContentViewModel
import com.michi.fridgetracker.viewmodel.adapters.IngredientsAdapter
import kotlinx.android.synthetic.main.fragment_fridge_content.*

class FridgeContentFragment : Fragment() {

    private lateinit var viewModel: FridgeContentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fridge_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FridgeContentViewModel::class.java)

        if (savedInstanceState == null) {
            viewModel.init()
        }

        addIngredient.setImageDrawable(
            ContextCompat.getDrawable(
                this.activity!!,
                R.drawable.ic_add
            )
        )
        addIngredient.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.action_fridgeContentFragment_to_addIngredient2,
                null
            )
        )

        ingredientsList.hasFixedSize()
        ingredientsList.layoutManager = LinearLayoutManager(ingredientsList.context)
        val adapter = IngredientsAdapter(viewModel)
        ingredientsList.adapter = adapter
        viewModel.getAllIngredients().observe(this, Observer { adapter.setIngredients(it) })
    }


}
