package com.uam.incrementovm.network

import android.content.Context
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.uam.incrementovm.db.AppDatabase
import com.uam.incrementovm.repository.LoginRepository
import com.uam.incrementovm.repository.UserRepository

/*object ServiceLocator {
    private val userApi = RetrofitInstance.userApi
    private val loginApi = RetrofitInstance.loginApi


    val loginRepository by lazy {
        LoginRepository(loginApi)
    }

    val userRepository by lazy {
        UserRepository(userApi)
    }
}*/
object ServiceLocator {
    private val userApi = RetrofitInstance.userApi
    private val loginApi = RetrofitInstance.loginApi
    @Volatile
    private var database: AppDatabase? = null
    private var repository: UserRepository? = null


    fun provideUserRepository(context: Context): UserRepository {
        val appContext = context.applicationContext
        val db = createDB(appContext)
        return repository ?: UserRepository(
            userApi = userApi,
            dao = db.userDao()
        ).also { repository = it}
    }

    private fun createDB(context:Context): AppDatabase{
        val db =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "user_database"
            ).build().also { database = it }
        return db
    }


    val loginRepository by lazy {
        LoginRepository(loginApi)
    }

}