package com.example.bukitasam


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.get
import com.example.bukitasam.retrofit.RetrofitInstance
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.bukitasam.Models.SignInBody

import com.example.bukitasam.retrofit.SessionManager
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
                if (loginresponse?.status==200) {
                    sessionManager.saveAuthToken(loginresponse.token)

                    val intent = Intent(this@LoginActivity, MainActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    startActivity(intent)


                } else {
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




//    private fun input(){
//        val btn_input=findViewById<Button>(R.id.btn_login)
//        btn_input.setOnClickListener {
//            val etemail =findViewById<EditText>(R.id.til_email)
//            val etpassword =findViewById<EditText>(R.id.til_password)
//            if (etemail.text.toString().isEmpty()){
////                etemail.setError()
//            }
//            if (etpassword.text.toString().isEmpty()){
//                etpassword.setError("Status Tidak boleh Kosong")
//            }
//                    signin()
//                }
//
//
////finish()
//    }
//    private fun signin(){
//        val etemail =findViewById<EditText>(R.id.til_email)
//        val etpassword =findViewById<EditText>(R.id.til_password)
//
//        val email=etemail.text.toString().trim()
//        val password=etpassword.text.toString().trim()
//
//        val retIn = RetrofitInstance.Create(this)
//        val signInInfo = SignInBody(email, password)
//        retIn.signin(signInInfo).enqueue(object : Callback<data> {
//            override fun onFailure(call: Call<data>, t: Throwable) {
//                Toast.makeText(
//                    this@LoginActivity,
//                    t.message,
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//            override fun onResponse(call: Call<data>, response: Response<ResponseBody>) {
//                if (response.code() == 200) {
//                    val intent = Intent(this@LoginActivity, MainActivity::class.java).also {
//                        it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                    }
//                    startActivity(intent)
//
//
//                } else {
//                    Log.d("Debug", "")
//                    etemail.setError(response.body()?.toString())
//                    Toast.makeText(this@LoginActivity, "Login failed!", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//        })
//
//
//    }




}
