package com.example.bukitasam.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.bukitasam.LoginActivity
import com.example.bukitasam.Models.data_checkin
import com.example.bukitasam.R
import com.example.bukitasam.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_data)
        hapushistory()

        val bt_hapus : Button = findViewById(R.id.btn_hapus)

        bt_hapus.setOnClickListener{
            SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Apakah anda Menghapus?")
                .setConfirmText("Yakin")
                .setConfirmClickListener {
                    deleteData()

                }
                .setCancelButton(
                    "Tidak"
                ) { sDialog -> sDialog.dismissWithAnimation() }
                .show()

        }
    }

    private fun hapushistory() {
        val tanggal: TextView = findViewById(R.id.dialog_tanggal)
        val lokasi: TextView = findViewById(R.id.Tv_lokasi)
        val nama: TextView = findViewById(R.id.tv_Nama)
        val tanggal2: TextView = findViewById(R.id.tv_tanggal2)
        val gambar : ImageView= findViewById(R.id.imageView10)



        tanggal.setText(intent.getStringExtra("tanggal"))
        lokasi.setText(intent.getStringExtra("lokasi"))
        nama.setText(intent.getStringExtra("nama"))
        tanggal2.setText(intent.getStringExtra("tanggal"))
        val gmb=intent.getStringExtra("gambar")
        val id = intent.getStringExtra("id")
        Glide.with(this@HistoryDataActivity).load(gmb)
            .transition(DrawableTransitionOptions().crossFade()).apply(RequestOptions())
            .placeholder(R.color.colorPrimaryDark)
            .into(gambar)


    }

    private fun deleteData(){
        val apiClient = RetrofitInstance.Create(this)
        val id=intent.getStringExtra("id")

        val deleteData = id?.let { apiClient.deletehistory(it.toInt()) }
        deleteData?.enqueue(object: Callback<data_checkin> {
            override fun onResponse(call: Call<data_checkin>, response: Response<data_checkin>) {
                val status_code= response.code()
                Log.d("delete",status_code.toString())
                if (response.isSuccessful) {
                            val intent = Intent(this@HistoryDataActivity,DashboardActivity::class.java).also {
                                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                            startActivity(intent)
                }
            }

            override fun onFailure(call: Call<data_checkin>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}