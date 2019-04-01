package com.michi.fridgetracker.persistance

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.michi.fridgetracker.domain.Ingredient

@Database(entities = [Ingredient::class], version = 1)
abstract class FridgeRoomDatabase : RoomDatabase() {

    abstract fun ingredientsDao(): IngredientsDao

    companion object {
        var INSTANCE: FridgeRoomDatabase? = null

        fun getAppDataBase(context: Context): FridgeRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(FridgeRoomDatabase::class) {
                    INSTANCE =
                        Room.databaseBuilder(context.applicationContext, FridgeRoomDatabase::class.java, "DB").build()
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
                val dao = db.ingredientsDao()
                dao.deleteAll()
                val ingredients = listOf(
                    Ingredient(name = "Spaghetti chili sauce", quantity = 1.0, price = 15.00),
                    Ingredient(name = "Salami Pork & Venison", quantity = 500.0, price = 30.00),
                    Ingredient(name = "Frozen bread rolls", quantity = 12.0, price = 11.00),
                    Ingredient(name = "Greek style coconut yogurt", quantity = 2.0, price = 8.00),
                    Ingredient(name = "Spaghetti chili sauce", quantity = 1.0, price = 15.00),
                    Ingredient(name = "Spaghetti chili sauce", quantity = 1.0, price = 15.00),
                    Ingredient(name = "Spaghetti chili sauce", quantity = 1.0, price = 15.00)
                )
                ingredients.forEach { dao.insert(it) }
            }

        }


    }

}