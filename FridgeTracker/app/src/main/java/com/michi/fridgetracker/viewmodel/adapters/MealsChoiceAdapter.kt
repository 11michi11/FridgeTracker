package com.michi.fridgetracker.viewmodel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.michi.fridgetracker.R
import com.michi.fridgetracker.domain.MealToChoose

class MealsChoiceAdapter : RecyclerView.Adapter<MealsChoiceAdapter.ViewHolder>() {

    private var meals: List<MealToChoose>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.choice_item, parent, false)
        return this.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (meals != null)
            return meals!!.size
        return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (meals != null) {
            with(meals!![position]) {
                holder.name.text = this.meal.name
                holder.checkBox.isChecked = this.isChecked
            }
        } else {
            holder.name.text = "Not ready yet"
            holder.checkBox.isChecked = false
        }
    }

    fun setMeals(meals: List<MealToChoose>) {
        this.meals = meals
        notifyDataSetChanged()
    }

    fun getMeals(): List<MealToChoose> = meals!!

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val name: TextView = itemView.findViewById(R.id.itemName)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkbox)

        init {
            checkBox.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            with(meals!!) {
                if (this[adapterPosition].isChecked) {
                    this[adapterPosition].isChecked = false
                    checkBox.isChecked = false
                } else {
                    this[adapterPosition].isChecked = true
                    checkBox.isChecked = true
                }
            }
        }

    }

}