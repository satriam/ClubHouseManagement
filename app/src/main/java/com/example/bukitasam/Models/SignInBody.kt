package com.example.bukitasam.Models

data class SignInBody(
    val id :Int,
    val status :Int,
    val email: String,
    val password: String,
    val token:String,
    val nama:String,

)

