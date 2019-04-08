package com.michi.fridgetracker.persistance

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.michi.fridgetracker.domain.Ingredient
import com.michi.fridgetracker.domain.Meal

@Database(entities = [Ingredient::class, Meal::class], version = 5)
abstract class FridgeRoomDatabase : RoomDatabase() {

    abstract fun ingredientsDao(): IngredientsDao
    abstract fun mealsDao(): MealsDao
    abstract fun mealsWithIngredientsDao(): MealWithIngredientsDao

    companion object {
        private var INSTANCE: FridgeRoomDatabase? = null

        fun getAppDataBase(context: Context): FridgeRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(FridgeRoomDatabase::class) {
                    INSTANCE =
                        Room.databaseBuilder(context.applicationContext, FridgeRoomDatabase::class.java, "DB")
                            .fallbackToDestructiveMigration() //update db with destroying current one
                            .addCallback(fridgeDatabaseCallback) // populate db
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase() {
            INSTANCE = null
        }

        private val fridgeDatabaseCallback = object : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                PopulateDbAsync(INSTANCE!!).execute()
            }
        }

        private class PopulateDbAsync(val db: FridgeRoomDatabase) : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg p0: Unit?) {
                val ingredientsDao = db.ingredientsDao()
                val mealsDao = db.mealsDao()
//                val mealWithIngredientsDao = db.mealsWithIngredientsDao()
                ingredientsDao.deleteAll()
                mealsDao.deleteAll()
//                mealWithIngredientsDao.deleteAll()
                val ingredients = mutableListOf(
                    Ingredient(name = "Spaghetti chili sauce", quantity = 1.0, price = 15.00),
                    Ingredient(name = "Greek style coconut yogurt", quantity = 2.0, price = 8.00),
                    Ingredient(name = "Spaghetti chili sauce", quantity = 1.0, price = 15.00),
                    Ingredient(name = "Spaghetti chili sauce", quantity = 1.0, price = 15.00),
                    Ingredient(name = "Spaghetti chili sauce", quantity = 1.0, price = 15.00)
                )

//                val salami = Ingredient(name = "Salami Pork & Venison", quantity = 500.0, price = 30.00)
//                val rolls = Ingredient(name = "Frozen bread rolls", quantity = 12.0, price = 11.00)
//
//                ingredients.addAll(listOf(salami, rolls))
                ingredients.forEach { ingredientsDao.insert(it) }
//                val meal = Meal(name = "Salami Sandwich")
//                mealsDao.insert(meal)
//
//                mealWithIngredientsDao.insert(MealWithIngredients(meal, listOf(salami, rolls)))
//
//                meal.ingredients = mealWithIngredientsDao.findAllIngredientsByMealId(meal.mealId)
//
//                Log.d("Database", meal.toString())
            }

        }


    }

}