package com.michi.fridgetracker.viewmodel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.michi.fridgetracker.R
import com.michi.fridgetracker.domain.IngredientToChoose
import com.michi.fridgetracker.viewmodel.ShoppingListViewModel
import kotlin.math.withSign

class ShoppingListAdapter(val viewModel: ShoppingListViewModel) :
    RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {

    private var ingredients: MutableList<IngredientToChoose>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.shopping_list_item, parent, false)
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

                holder.quantity.text = invertQuantityToString(this.ingredient.quantity)
            }
        } else {
            holder.name.text = "Not ready yet"
            holder.checkBox.isChecked = false
            holder.quantity.text = "0"
        }
    }

    fun setIngredients(ingredients: List<IngredientToChoose>) {
        this.ingredients = ingredients.toMutableList()
        this.ingredients!!.sortedBy { it.isChecked }
        notifyDataSetChanged()
    }

    fun getIngredients(): List<IngredientToChoose> = ingredients!!

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val name: TextView = itemView.findViewById(R.id.itemName)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkbox)
        val quantity: TextView = itemView.findViewById(R.id.itemQuantity)

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
                    viewModel.addIngredientQuantity(this[adapterPosition].ingredient.ingredientId, invertQuantity(quantity.text.toString().toDouble()))
                    this.removeAt(adapterPosition)
                    notifyDataSetChanged()
                }
            }
        }

    }

    private fun invertQuantityToString(qty: Double): String {
        return invertQuantity(qty).toString()
    }

    private fun invertQuantity(qty: Double): Double {
        return qty.withSign(1)
    }

}
