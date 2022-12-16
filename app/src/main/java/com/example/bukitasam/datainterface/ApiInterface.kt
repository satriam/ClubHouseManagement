package com.example.bukitasam.datainterface

import com.example.bukitasam.Models.*

import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

        @FormUrlEncoded
        @POST("login")
        fun signin(
            @Field("email") email:String,
            @Field("password") password:String
        ): Call<SignInBody>

    @FormUrlEncoded
    @POST("register")
    fun tambah(
        @Field("email") email:String,
        @Field("password") password:String,
        @Field("nama") nama:String,
        @Field("role_id") role_id:String,
    ): Call<SignInBody>


        @GET("checkin")
        fun getcheckin(): Call<ArrayList<data_checkin>>

    @GET("auth")
    fun getUser(): Call<ArrayList<UserModel>>

    @FormUrlEncoded
    @PUT("auth/{id}")
    fun UpdateUser(
        @Path("id")id:Int?,
//        @Field("jenis") jenis:String?,
        @Field("role_id") role_id:String?,
    ): Call<UserModel>

    @DELETE("readcheckin/{id}")
    fun deletehistory(
        @Path("id")id:Int
    ):Call<data_checkin>



    @GET("checkin")
    fun getcheckinadmin(): Call<ArrayList<data_checkin_admin>>

    @FormUrlEncoded
    @POST("checkin")
    fun createcheckin(
        @Field("kode_lokasi") kode_lokasi:String
    ):Call<data_checkin>

    @FormUrlEncoded
    @POST("checkin")
    fun createcheckinadmin(
        @Field("kode_lokasi") kode_lokasi:String
    ):Call<data_checkin_admin>

//    @GET("me")
//    fun getprof():Call<ArrayList<Profile_models>>

}




