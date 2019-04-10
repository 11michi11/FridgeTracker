package com.michi.fridgetracker.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.michi.fridgetracker.R
import com.michi.fridgetracker.domain.Meal

class MenuAdapter(private val viewModel: MenuViewModel) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    private var meals: List<Meal>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.menu_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (meals != null)
            return meals!!.size
        return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (meals != null)
            holder.name.text = meals!![position].name
        else
            holder.name.text = "Not ready yet"

    }

    fun setMeals(meals: List<Meal>) {
        this.meals = meals
        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.mealName)

        init {
            val delete: ImageButton = itemView.findViewById(R.id.deleteMeal)
            val edit: ImageButton = itemView.findViewById(R.id.editMeal)

            delete.setOnClickListener{deleteMeal(it)}
            edit.setOnClickListener{editMeal(it)}

        }

        private fun deleteMeal(view: View){
            viewModel.delete(meals?.get(adapterPosition))
        }

        private fun editMeal(view: View){

        }


    }


}