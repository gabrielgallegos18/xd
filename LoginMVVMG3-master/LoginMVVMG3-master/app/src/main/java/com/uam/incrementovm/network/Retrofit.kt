package com.uam.incrementovm.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val BASE_URL : String = "http://10.0.2.2:8181/api/"

val client = OkHttpClient.Builder()
    .connectTimeout(10,TimeUnit.SECONDS)
    .readTimeout(15,TimeUnit.SECONDS)
    .writeTimeout(15,TimeUnit.SECONDS)
    .build()

object RetrofitInstance {
    val api: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val userApi : UserApi by lazy {
        api.create(UserApi::class.java)
    }
    val loginApi : LoginApi by lazy {
        api.create(LoginApi::class.java)
    }
}