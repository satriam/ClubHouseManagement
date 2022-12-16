package com.example.bukitasam.retrofit

import android.content.Context
import android.content.SharedPreferences
import com.example.bukitasam.R

class SessionManager(context: Context){
    val PRIVATE_MODE = 0
    private val IS_LOGIN = "is_login"
    private var prefs: SharedPreferences=context.getSharedPreferences(context.getString(R.string.app_name),Context.MODE_PRIVATE)
    var editor: SharedPreferences.Editor? = prefs?.edit()
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
    fun setLoggin(isLogin: Boolean) {
        editor?.putBoolean(IS_LOGIN, isLogin)
        editor?.commit()
    }
    fun setrole(role: String?) {
        editor?.putString("role_id", role)
        editor?.commit()
    }

    fun isLogin(): Boolean? {
        return prefs?.getBoolean(IS_LOGIN, false)
    }

    fun getrole(): String? {
        return prefs?.getString("role_id", "")
    }

    fun removeData() {
        editor?.clear()
        editor?.commit()
    }

    fun setnama(nama: String?) {
        editor?.putString("nama", nama)
        editor?.commit()
    }
    fun setnopeg(nopeg: String?) {
        editor?.putString("nopeg", nopeg)
        editor?.commit()
    }
    fun getnama(): String? {
        return prefs?.getString("nama", "")
    }
    fun getnopeg(): String? {
        return prefs?.getString("nopeg", "")
    }
}