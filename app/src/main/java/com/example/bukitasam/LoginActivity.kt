package com.example.bukitasam


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.bukitasam.Admin.MainAdminActivity
import com.example.bukitasam.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.bukitasam.Models.SignInBody

import com.example.bukitasam.retrofit.SessionManager
import com.example.bukitasam.user.MainActivity
import com.google.android.material.textfield.TextInputLayout


class LoginActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager  = SessionManager(this)
        setContentView(R.layout.activity_login)
        auth()
    }


private fun auth(){
    val apiClient = RetrofitInstance.Create(this)

    val etemail =findViewById<EditText>(R.id.til_email)
    val etpassword =findViewById<TextInputLayout>(R.id.til_password)
    val btnlogin =findViewById<Button>(R.id.btn_login)

    btnlogin.setOnClickListener{
        apiClient.signin(
            etemail.text.toString(),
            etpassword.getEditText()?.getText().toString().trim()
        ).enqueue(object : Callback<SignInBody>{
            override fun onResponse(call: Call<SignInBody>, response: Response<SignInBody>) {
                val loginresponse =response.body()
                if (loginresponse?.status==200 && loginresponse?.role_id==1 ) {
                    sessionManager.saveAuthToken(loginresponse.token)

                    val intent = Intent(this@LoginActivity, MainAdminActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    startActivity(intent)


                }else if(loginresponse?.status==200 && loginresponse?.role_id==2) {
                    sessionManager.saveAuthToken(loginresponse.token)

                    val intent = Intent(this@LoginActivity, MainActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    startActivity(intent)
                }
                else {
                    Log.d("Debug", "")
                    Toast.makeText(this@LoginActivity, "Login failed!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SignInBody>, t: Throwable) {
                Toast.makeText(
                    this@LoginActivity,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

}

}
