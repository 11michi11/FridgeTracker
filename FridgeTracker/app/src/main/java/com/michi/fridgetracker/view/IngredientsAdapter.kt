package com.michi.fridgetracker.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.michi.fridgetracker.R
import com.michi.fridgetracker.domain.Ingredient

class IngredientsAdapter(private val ingredients: List<Ingredient>) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.ingredients_list_item, parent, false)
        return ViewHolder(view) {
            Toast.makeText(parent.context, "Clicked ${ingredients[it].name}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    override fun onBindViewHolder(viewHolder: IngredientsAdapter.ViewHolder, position: Int) {
        viewHolder.name.text = ingredients[position].name
        viewHolder.quantity.text = ingredients[position].quantity.toString()
        viewHolder.price.text = ingredients[position].price.toString()
    }


    class ViewHolder(itemView: View, private val listener: (Int) -> Unit) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val name: TextView = itemView.findViewById(R.id.ingredientName)
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
