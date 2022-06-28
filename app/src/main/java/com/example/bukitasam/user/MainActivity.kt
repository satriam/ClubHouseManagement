package com.example.bukitasam.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bukitasam.LoginActivity

import com.example.bukitasam.Models.data_checkin
import com.example.bukitasam.R
import com.example.bukitasam.adapter.AdapterCheckin
import com.example.bukitasam.retrofit.RetrofitInstance
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val TIME_INTERVAL = 2000
    private var mBackPressed: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getCheckinData()

        val btnQR = findViewById<FloatingActionButton>(R.id.fab_qr)

        btnQR.setOnClickListener{
            val intent= Intent(this, QRScanActivity::class.java)
            startActivity(intent)

        }
    }

    override fun onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed()
            return
        } else {
            Toast.makeText(baseContext, "Tekan Back Sekali lagi untuk Keluar", Toast.LENGTH_SHORT)
                .show()
        }
        mBackPressed = System.currentTimeMillis()
    }

override fun onResume() {
 getCheckinData()
    super.onResume()
}





    private fun getCheckinData(){
        val listData = ArrayList<data_checkin>()
        val rvcheckin= findViewById<RecyclerView>(R.id.rv_checkin)
        rvcheckin.setHasFixedSize(true)
        rvcheckin.layoutManager= LinearLayoutManager(this)
        val apiClient = RetrofitInstance.Create(this)
        val callData =apiClient.getcheckin()

        callData.enqueue(object : Callback<ArrayList<data_checkin>> {
            override fun onResponse(
                call: Call<ArrayList<data_checkin>>,
                response: Response<ArrayList<data_checkin>>
            ) {
                val data = response.body()
                data?.let { listData.addAll(it) }
                val adapterData = AdapterCheckin(listData)
                rvcheckin.adapter=adapterData
                Log.d("DATA",data.toString())

            }

            override fun onFailure(call: Call<ArrayList<data_checkin>>, t: Throwable) {
                Log.e("ERROR", t.message.toString())
            }

        })
    }



}