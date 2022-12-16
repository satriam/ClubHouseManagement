package com.example.bukitasam


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.bukitasam.Admin.DashboardActivity
import com.example.bukitasam.Admin.MainAdminActivity
import com.example.bukitasam.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.bukitasam.Models.SignInBody

import com.example.bukitasam.retrofit.SessionManager
import com.example.bukitasam.user.DashboardUserActivity
import com.example.bukitasam.user.MainActivity
import com.google.android.material.textfield.TextInputLayout


class LoginActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager  = SessionManager(this)
        setContentView(R.layout.activity_login)
        onStart()
        checklogin()
        auth()


    }

    private fun checklogin(){
        val apiClient = RetrofitInstance.Create(this)
        val callData =apiClient.getcheckin()
        if (sessionManager.isLogin()!! && sessionManager.getrole()=="admin kolam"){

            val intent = Intent(this@LoginActivity, DashboardActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()

        }else if (sessionManager.isLogin()!! && sessionManager.getrole()=="admin billiard"){
            val intent = Intent(this@LoginActivity, DashboardActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
        }else if (sessionManager.isLogin()!! && sessionManager.getrole()=="user"){
            val intent = Intent(this@LoginActivity, DashboardUserActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
        }
    }

private fun auth(){
    sessionManager = SessionManager(this)
    val apiClient = RetrofitInstance.Create(this)

    val etemail =findViewById<EditText>(R.id.til_email)
    val etpassword =findViewById<TextInputLayout>(R.id.til_password)
    val btnlogin =findViewById<Button>(R.id.btn_login)

    btnlogin.setOnClickListener{
        apiClient.signin(
            etemail.text.toString().trim(),
            etpassword.getEditText()?.getText().toString().trim()
        ).enqueue(object : Callback<SignInBody>{
            override fun onResponse(call: Call<SignInBody>, response: Response<SignInBody>) {
                val loginresponse =response.body()
                if (loginresponse?.status==200 && loginresponse?.role_id==1  ) {
                    sessionManager.saveAuthToken(loginresponse.token)
                    sessionManager.setLoggin(true)
                    sessionManager.setrole("admin kolam")
                    sessionManager.setnama(loginresponse.name)
                    sessionManager.setnopeg(loginresponse.no_pegawai)
                    SweetAlertDialog(this@LoginActivity, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Selamat datang!")
                        .setContentText("Berhasil Login")
                        .setConfirmClickListener {
                            val intent = Intent(this@LoginActivity, DashboardActivity::class.java).also {
                                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                            startActivity(intent)
                        }
                        .show()



                }else if (loginresponse?.status==200 && loginresponse?.role_id==3  ) {
                    sessionManager.saveAuthToken(loginresponse.token)
                    sessionManager.setLoggin(true)
                    sessionManager.setrole("admin billiard")
                    sessionManager.setnama(loginresponse.name)
                    sessionManager.setnopeg(loginresponse.no_pegawai)

                    SweetAlertDialog(this@LoginActivity, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Selamat datang!")
                        .setContentText("Berhasil Login")
                        .setConfirmClickListener {
                            val intent = Intent(this@LoginActivity, DashboardActivity::class.java).also {
                                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                            startActivity(intent)
                        }
                        .show()


                }else if(loginresponse?.status==200 && loginresponse?.role_id==2) {

                    sessionManager.saveAuthToken(loginresponse.token)
                    sessionManager.setLoggin(true)
                    sessionManager.setrole("user")
                    sessionManager.setnama(loginresponse.name)
                    sessionManager.setnopeg(loginresponse.no_pegawai)
                    SweetAlertDialog(this@LoginActivity, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Selamat datang!")
                        .setContentText("Berhasil Login")
                        .setConfirmClickListener( {
                            val intent = Intent(this@LoginActivity, DashboardUserActivity::class.java)
                            startActivity(intent)
                            finish()
                        })
                        .show()
                }
                else {
                    SweetAlertDialog(this@LoginActivity, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Silahkan Cek Password/Email")
                        .show()
                }

            }

            override fun onFailure(call: Call<SignInBody>, t: Throwable) {
                SweetAlertDialog(this@LoginActivity, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Something went wrong!")
                    .show()
            }

        })
    }

}

}
