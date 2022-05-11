package com.example.bukitasam.datainterface

import com.example.bukitasam.Models.SignInBody

import com.example.bukitasam.Models.data_checkin
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

        @FormUrlEncoded
        @POST("login")
        fun signin(
            @Field("email") email:String,
            @Field("password") password:String
        ): Call<SignInBody>


        @GET("checkin")
        fun getcheckin(): Call<ArrayList<data_checkin>>

    @FormUrlEncoded
    @POST("checkin")
    fun createcheckin(
        @Field("kode_lokasi") kode_lokasi:String
    ):Call<data_checkin>
    }

