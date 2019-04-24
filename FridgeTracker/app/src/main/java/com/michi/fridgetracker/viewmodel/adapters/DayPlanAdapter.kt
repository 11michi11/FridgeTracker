package com.michi.fridgetracker.viewmodel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.michi.fridgetracker.R
import com.michi.fridgetracker.domain.Meal
import com.michi.fridgetracker.viewmodel.DayPlanViewModel

class DayPlanAdapter(val viewModel: DayPlanViewModel) : RecyclerView.Adapter<DayPlanAdapter.ViewHolder>() {

    private var meals: List<Meal>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.day_plan_item, parent, false)
        return ViewHolder(view) {
            Toast.makeText(parent.context, "Clicked ${meals!![it]}", Toast.LENGTH_SHORT).show()
        }
    }

    fun setMeals(meals: List<Meal>) {
        this.meals = meals
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        if (meals != null)
            return meals!!.size
        return 0
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (meals != null) {
            viewHolder.name.text = meals!![position].name
        } else {
            viewHolder.name.text = "Not ready yet"
        }
    }


    inner class ViewHolder(itemView: View, private val listener: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val name: TextView = itemView.findViewById(R.id.mealName)
        val delete: ImageButton = itemView.findViewById(R.id.deleteMeal)

        init {
            itemView.setOnClickListener(this)
            delete.setOnClickListener {
                viewModel.deleteMeal(adapterPosition)
            }
        }

        override fun onClick(view: View?) {
            listener.invoke(adapterPosition)
        }

        //Add delete button

    }

}