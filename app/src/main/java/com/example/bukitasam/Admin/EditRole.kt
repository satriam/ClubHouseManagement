package com.example.bukitasam.Admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.bukitasam.Models.UserModel
import com.example.bukitasam.R
import com.example.bukitasam.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditRole : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_role)
        updateuser()
    }
    private fun updateuser() {
        val etNama: EditText = findViewById(R.id.et_nama)
        val etRole: EditText = findViewById(R.id.et_role)
        val etRolenama:EditText=findViewById(R.id.et_rolenama)
        val btnupdate: Button = findViewById(R.id.btn_update_role)


        etNama.setText(intent.getStringExtra("nama"))
        etRolenama.setText(intent.getStringExtra("role"))
        etRole.setText(intent.getStringExtra("role_id"))
        val id=intent.getStringExtra("id")

        btnupdate.setOnClickListener {
            val apiClient = RetrofitInstance.Create(this)

            val sendData = apiClient.UpdateUser(
                id?.toInt(),
                etRole.text.toString()

            )

            if (etRole.text.toString().isEmpty()){
                etRole.setError("Role Tidak boleh Kosong")
            }

            sendData.enqueue(object : Callback<UserModel> {
                override fun onResponse(
                    call: Call<UserModel>,
                    response: Response<UserModel>
                ) {
                    val status_code = response.code()
                    Log.d("EDIT", status_code.toString())
                    if (response.isSuccessful) {
                        Toast.makeText(
                            applicationContext,
                            "Data berhasil diupdate",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                }

                override fun onFailure(call: Call<UserModel>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
}