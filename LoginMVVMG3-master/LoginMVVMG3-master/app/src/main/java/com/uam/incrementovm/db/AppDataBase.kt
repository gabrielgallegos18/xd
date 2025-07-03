package com.uam.incrementovm.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.uam.incrementovm.dao.UserDao
import com.uam.incrementovm.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}