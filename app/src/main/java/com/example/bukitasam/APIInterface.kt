package com.example.bukitasam

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface APIInterface {
    @GET("checkin")
    fun getcheckin(): Call<ArrayList<CekinModel>>

    //get
    @GET("checkin/{id}")
    fun getStudent(
        @Path("id")id:Int
    ): Call<ArrayList<CekinModel>>

}