package com.michi.fridgetracker.persistance

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.michi.fridgetracker.domain.Ingredient
import com.michi.fridgetracker.domain.IngredientToChoose
import com.michi.fridgetracker.domain.Meal

class IngredientsRepository(application: Application) {

    private var ingredientsDao: IngredientsDao
    private var ingredients: LiveData<List<Ingredient>>

    companion object {
        private var INSTANCE: IngredientsRepository? = null

        fun getInstance(application: Application): IngredientsRepository {
            if (INSTANCE == null) {
                synchronized(IngredientsRepository::class) {
                    INSTANCE = IngredientsRepository(application)
                }
            }
            return INSTANCE!!
        }
    }

    init {
        val db = FridgeRoomDatabase.getAppDataBase(application)
        ingredientsDao = db!!.ingredientsDao()
        ingredients = ingredientsDao.findAll()
    }

    public fun getAllIngredients() = ingredients

    public fun getAllIngredientsToChoose(): LiveData<List<IngredientToChoose>> {
        val all = ingredientsDao.findAll()
        val ingredientsMediator  = MediatorLiveData<List<IngredientToChoose>>()

        ingredientsMediator.addSource(all) {
            if(it != null){
                ingredientsMediator.removeSource(all)
                ingredientsMediator.value = it.map { ingredient -> IngredientToChoose(ingredient) }
            }
        }

        return ingredientsMediator
    }

    fun getAllShoppingIngredients(): LiveData<List<IngredientToChoose>> {
        val all = ingredientsDao.findAllShoppingIngredients()
        val ingredientsMediator  = MediatorLiveData<List<IngredientToChoose>>()

        ingredientsMediator.addSource(all) {
            if(it != null){
                ingredientsMediator.removeSource(all)
                ingredientsMediator.value = it.map { ingredient -> IngredientToChoose(ingredient) }
            }
        }

        return ingredientsMediator
    }

    public fun insert(ingredient: Ingredient) {
        InsertAsyncTask(ingredientsDao).execute(ingredient)
    }

    fun adjustIngredientsQuantityFromMeals(meals: List<Meal>) {
        AdjustIngredientQuantityAsyncTask(ingredientsDao).execute(meals)
    }

    fun addIngredientQuantity(ingredientId: Int, quantity: Double) {
        AddIngredientQuantity(ingredientsDao).execute(IngredientWithQuantity(ingredientId, quantity))
    }

    private class AddIngredientQuantity(val ingredientsDao: IngredientsDao) : AsyncTask<IngredientWithQuantity, Unit, Unit>() {

        override fun doInBackground(vararg data: IngredientWithQuantity?) {
            val (ingredientId, quantity) = data[0]!!
            val ingredient = ingredientsDao.findById(ingredientId)

            if(ingredient.quantity <= 0)
                ingredient.quantity = quantity
            else
                ingredient.quantity += quantity

            ingredientsDao.update(ingredient)
        }
    }

    private class InsertAsyncTask(val ingredientsDao: IngredientsDao) : AsyncTask<Ingredient, Unit, Unit>() {

        override fun doInBackground(vararg ingredient: Ingredient?) {
            ingredientsDao.insert(ingredient[0]!!)
        }
    }

    private class AdjustIngredientQuantityAsyncTask(val ingredientsDao: IngredientsDao) : AsyncTask<List<Meal>, Unit, Unit>() {

        override fun doInBackground(vararg data: List<Meal>?) {
            val meals = data[0]!!
            val ingredients = ingredientsDao.getAll()

            //TODO Very bad function. Possible optimization here
            meals.forEach {
                it.ingredients.forEach {
                    val ingredientId = it.ingredient.ingredientId
                    val ingredient = ingredients.find { it.ingredientId == ingredientId }

                    if (ingredient != null)
                        ingredient.quantity -= it.ingredient.quantity
                }
            }

            ingredients.forEach { ingredientsDao.update(it) }
        }
    }
}

data class IngredientWithQuantity(val ingredientId: Int, val quantity: Double)