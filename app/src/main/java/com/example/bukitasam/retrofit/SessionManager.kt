package com.example.bukitasam.retrofit

import android.content.Context
import android.content.SharedPreferences
import com.example.bukitasam.R

class SessionManager(context: Context){

    private var prefs: SharedPreferences=context.getSharedPreferences(context.getString(R.string.app_name),Context.MODE_PRIVATE)
    companion object{
        const val akses_token="Token"
    }

    fun saveAuthToken(token:String){
        val editor=prefs.edit()
        editor.putString(akses_token,token)
        editor.apply()
    }
    fun fetchAuthToken():String?{
        return prefs.getString(akses_token,null)
    }
}