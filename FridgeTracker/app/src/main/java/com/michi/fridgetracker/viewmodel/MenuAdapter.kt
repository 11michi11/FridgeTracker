package com.michi.fridgetracker.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.michi.fridgetracker.R
import com.michi.fridgetracker.domain.Meal

class MenuAdapter : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    private var meals: List<Meal>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.menu_list_item, parent, false)
        return ViewHolder(view) {
            Toast.makeText(parent.context, "Clicked ${meals!![it].name}", Toast.LENGTH_SHORT).show()
        }
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


    class ViewHolder(itemView: View, private val listener: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val name: TextView = itemView.findViewById(R.id.mealName)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            listener.invoke(adapterPosition)
        }

    }


}