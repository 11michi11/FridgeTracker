package com.michi.fridgetracker.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.michi.fridgetracker.R
import com.michi.fridgetracker.domain.IngredientToChoose

class IngredientsChoiceAdapter : RecyclerView.Adapter<IngredientsChoiceAdapter.ViewHolder>() {

    private var ingredients: List<IngredientToChoose>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.ingredients_choice_item, parent, false)
        return this.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (ingredients != null)
            return ingredients!!.size
        return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (ingredients != null) {
            with(ingredients!![position]) {
                holder.name.text = this.ingredient.name
                holder.checkBox.isChecked = this.isChecked
            }
        } else {
            holder.name.text = "Not ready yet"
            holder.checkBox.isChecked = false
        }
    }

    fun setIngredients(ingredients: List<IngredientToChoose>) {
        this.ingredients = ingredients
        notifyDataSetChanged()
    }

    fun getIngredients(): List<IngredientToChoose> = ingredients!!

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val name: TextView = itemView.findViewById(R.id.ingredientNameChoice)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkbox)

        init {
            checkBox.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            with(ingredients!!) {
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