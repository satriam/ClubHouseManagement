package com.example.bukitasam.retrofit

import android.content.Context
import com.example.bukitasam.datainterface.ApiInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    const val BASE_URL= "https://sfourt.000webhostapp.com/"
    fun Create(context: Context):ApiInterface{
        val retrofit= Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiInterface::class.java)
    }


    private fun okhttpClient(context: Context):OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()
    }


}