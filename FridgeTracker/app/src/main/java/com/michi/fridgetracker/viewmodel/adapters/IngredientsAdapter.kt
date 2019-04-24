package com.michi.fridgetracker.viewmodel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.michi.fridgetracker.R
import com.michi.fridgetracker.domain.Ingredient
import android.view.Gravity
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.widget.*
import android.widget.Toast.LENGTH_SHORT
import com.michi.fridgetracker.viewmodel.IngredientSavableViewModel
import java.lang.NumberFormatException


class IngredientsAdapter(val viewModel: IngredientSavableViewModel) :
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


    inner class ViewHolder(itemView: View, private val listener: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val name: TextView = itemView.findViewById(R.id.itemName)
        val quantity: TextView = itemView.findViewById(R.id.ingredientQuantity)
        val price: TextView = itemView.findViewById(R.id.ingredientPrice)
        private val editIngredient: ImageButton = itemView.findViewById(R.id.editIngrBtn)

        init {
            itemView.setOnClickListener(this)
            editIngredient.setOnClickListener {
                showPopup(itemView)
            }
        }

        override fun onClick(view: View?) {
            listener.invoke(adapterPosition)
        }

        private fun showPopup(view: View) {
            // inflate the layout of the popup window
            val inflater = view.context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater?
            val popupView = inflater!!.inflate(R.layout.edit_quantity, null)

            val saveButton: Button = popupView.findViewById(R.id.saveQuantity)
            val quantityAmount: EditText = popupView.findViewById(R.id.quantityAmount)
            quantityAmount.text.clear()
            quantityAmount.text.insert(0, quantity.text)

            // create the popup window
            val width = LinearLayout.LayoutParams.WRAP_CONTENT
            val height = LinearLayout.LayoutParams.WRAP_CONTENT
            val focusable = true // lets taps outside the popup also dismiss it
            val popupWindow = PopupWindow(popupView, width, height, focusable)

            // show the popup window
            // which view you pass in doesn't matter, it is only used for the window tolken
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

            // dismiss the popup window when touched
            popupView.setOnTouchListener { v, event ->
                popupWindow.dismiss()
                true
            }


            saveButton.setOnClickListener {
                try{
                    val ingredientQuantity = quantityAmount.text.toString().toDouble()
                    viewModel.saveIngredientQuantity(ingredientQuantity, adapterPosition)
                    popupWindow.dismiss()
                }catch (e: NumberFormatException){
                    Toast.makeText(view.context, "Value must be a number", LENGTH_SHORT).show()
                }
            }
        }

    }

}
