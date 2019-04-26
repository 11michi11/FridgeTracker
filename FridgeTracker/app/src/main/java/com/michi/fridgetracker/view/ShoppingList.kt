package com.michi.fridgetracker.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.michi.fridgetracker.R
import com.michi.fridgetracker.viewmodel.ShoppingListViewModel
import com.michi.fridgetracker.viewmodel.adapters.ShoppingListAdapter
import kotlinx.android.synthetic.main.shopping_list_fragment.*

class ShoppingList : Fragment() {

    companion object {
        fun newInstance() = ShoppingList()
    }

    private lateinit var viewModel: ShoppingListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.shopping_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ShoppingListViewModel::class.java)

        if (savedInstanceState == null) {
            viewModel.init()
        }


        shoppingList.hasFixedSize()
        shoppingList.layoutManager = LinearLayoutManager(shoppingList.context)
        val adapter = ShoppingListAdapter(viewModel)
        shoppingList.adapter = adapter
        viewModel.getAllIngredients().observe(this, Observer { adapter.setIngredients(it) })
    }

}
