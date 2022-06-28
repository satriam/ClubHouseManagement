package com.example.bukitasam.Admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.bukitasam.Models.SignInBody
import com.example.bukitasam.R
import com.example.bukitasam.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        Register()
    }
    private fun Register() {

        val etNama: EditText = findViewById(R.id.et_nama)
        val etpassword: EditText = findViewById(R.id.et_password)
        val etemail: EditText = findViewById(R.id.et_email)
        val role:EditText=findViewById(R.id.roleid)

        val btnsave: Button = findViewById(R.id.btn_daftar)

        btnsave.setOnClickListener {
            val apiClient = RetrofitInstance.Create(this)
            val sendData = apiClient.tambah(
                etemail.text.toString(),
                etpassword.text.toString(),
                etNama.text.toString(),
                role.text.toString()
            )
            if (etNama.text.toString().isEmpty()){
                etNama.setError("Nama Tidak boleh Kosong")
            }
            if (etpassword.text.toString().isEmpty()){
                etpassword.setError("password Tidak boleh Kosong")
            }
            if (etemail.text.toString().isEmpty()){
                etemail.setError("Email Tidak boleh Kosong")
            }
            sendData.enqueue(object : Callback<SignInBody> {
                override fun onResponse(
                    call: Call<SignInBody>,
                    response: Response<SignInBody>
                ) {
                    val status_code = response.code()
                    Log.d("INPUT", sendData.toString())
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Data berhasil disimpan",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                }

                override fun onFailure(call: Call<SignInBody>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }

    }
}