package com.example.nutritiontracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [User::class, FoodEntry::class],
    version = 1,
    exportSchema = false
)
abstract class NutritionDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun foodDao(): FoodDao
    
    companion object {
        @Volatile
        private var INSTANCE: NutritionDatabase? = null
        
        fun getDatabase(context: Context): NutritionDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NutritionDatabase::class.java,
                    "nutrition_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}