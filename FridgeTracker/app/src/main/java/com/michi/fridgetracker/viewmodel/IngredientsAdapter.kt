package com.michi.fridgetracker.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.michi.fridgetracker.R
import com.michi.fridgetracker.domain.Ingredient

class IngredientsAdapter :
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    private var ingredients: List<Ingredient>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.ingredients_list_item, parent, false)
        return ViewHolder(view) {
            Toast.makeText(parent.context, "Clicked ${ingredients!![it].name}", Toast.LENGTH_SHORT).show()
        }
    }

    fun setIngredients(ingredients: List<Ingredient>) {
        this.ingredients = ingredients
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        if (ingredients != null)
            return ingredients!!.size
        return 0
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (ingredients != null) {
            viewHolder.name.text = ingredients!![position].name
            viewHolder.quantity.text = ingredients!![position].quantity.toString()
            viewHolder.price.text = ingredients!![position].price.toString()
        } else {
            viewHolder.name.text = "Not ready yet"
        }
    }


    class ViewHolder(itemView: View, private val listener: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val name: TextView = itemView.findViewById(R.id.ingredientNameChoice)
        val quantity: TextView = itemView.findViewById(R.id.ingredientQuantity)
        val price: TextView = itemView.findViewById(R.id.ingredientPrice)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            listener.invoke(adapterPosition)
        }

    }

}
