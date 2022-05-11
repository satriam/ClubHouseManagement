package com.example.bukitasam.retrofit

import android.content.Context
import com.example.bukitasam.datainterface.ApiInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//class RetrofitInstance {
//    companion object {
//        val BASE_URL: String = "http://192.168.1.15:8080/"

//        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
//            this.level = HttpLoggingInterceptor.Level.BODY
//        }

//        val client: OkHttpClient = OkHttpClient.Builder().apply {
//            this.addInterceptor(interceptor)
//        }.build()
//        fun getRetrofitInstance(): Retrofit {
//            return Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//        }
//    }
//}

object RetrofitInstance {
    const val BASE_URL= "http://192.168.1.136:8080/"
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