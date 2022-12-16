package com.example.bukitasam.Admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.bukitasam.LoginActivity
import com.example.bukitasam.Models.UserModel
import com.example.bukitasam.R
import com.example.bukitasam.retrofit.RetrofitInstance
import com.example.bukitasam.retrofit.SessionManager
import kotlinx.android.synthetic.main.activity_edit_role.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EditRole : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager  = SessionManager(this)
        setContentView(R.layout.activity_edit_role)
        updateuser()

    }

    private fun updateuser() {
        val etNama: TextView = findViewById(R.id.tv_Name)
        val etRole: EditText = findViewById(R.id.et_role)
        val etRolenama: TextView = findViewById(R.id.Tv_role)
        val email:TextView=findViewById(R.id.tv_email)
        val btnupdate: Button = findViewById(R.id.bt_gpass)
        val spinner = findViewById<Spinner>(R.id.spinner)

        etNama.setText(intent.getStringExtra("nama"))
        etRolenama.setText(intent.getStringExtra("jenis"))
        email.setText(intent.getStringExtra("email"))
        val id = intent.getStringExtra("id")



        btnupdate.setOnClickListener {
            SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Apakah anda yakin untuk Simpan?")
                .setConfirmText("Yakin")
                .setConfirmClickListener {
                    if (spinner.selectedItem == "admin kolam") {
                        val ganti = "1"
                        etRole.setText(ganti)
                    } else if (spinner.selectedItem == "user") {
                        val ganti = "2"
                        etRole.setText(ganti)
                    } else if (spinner.selectedItem == "admin biliard") {
                        val ganti = "3"
                        etRole.setText(ganti)
                    }


                    val apiClient = RetrofitInstance.Create(this)

                    val sendData = apiClient.UpdateUser(
                        id?.toInt(),
                        etRole.text.toString()
                    )
                    if (etRole.text.toString().isEmpty()) {
                        SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Edit Role Gagal")
                            .setContentText("Harap Pilih Role")
                            .setConfirmClickListener { sDialog -> sDialog.dismissWithAnimation() }
                            .show()
                    } else {

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
                                        "Data Berhasil Diubah",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                                TODO("Not yet implemented")
                            }

                        })
                        finish()
                    }
                }
                .setCancelButton(
                    "Tidak"
                ) { sDialog -> sDialog.dismissWithAnimation() }
                .show()


        }
    }
}