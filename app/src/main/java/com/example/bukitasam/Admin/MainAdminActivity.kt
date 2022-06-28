package com.example.bukitasam.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bukitasam.*
import com.example.bukitasam.Models.data_checkin
import com.example.bukitasam.adapter.AdapterCheckinAdmin
import com.example.bukitasam.retrofit.RetrofitInstance
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainAdminActivity : AppCompatActivity() {
    private val TIME_INTERVAL = 2000
    private var mBackPressed: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_admin)
        getcheckinData()

        val btnQR = findViewById<FloatingActionButton>(R.id.fab_qr)

        btnQR.setOnClickListener{
            val intent= Intent(this, AdminScanActivity::class.java)
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
        getcheckinData()
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navigationadmin,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.User->{
                startActivity(Intent(this,UserActivity::class.java))
                true

            }
           R.id.navigation_register->{
                startActivity(Intent(this,RegisterActivity::class.java))
                true
            }else->super.onOptionsItemSelected(item)
        }
    }
    private fun getcheckinData(){
        val listData = ArrayList<data_checkin>()
        val rvStudents: RecyclerView =findViewById(R.id.rv_checkin)
        rvStudents.setHasFixedSize(true)
        rvStudents.layoutManager= LinearLayoutManager(this)
        val apiClient = RetrofitInstance.Create(this)
        val callData = apiClient.getcheckinadmin()

        callData.enqueue(object : Callback<ArrayList<data_checkin>> {
            override fun onResponse(
                call: Call<ArrayList<data_checkin>>,
                response: Response<ArrayList<data_checkin>>
            ) {
                val data = response.body()
                val status_code= response.code()

                data?.let { listData.addAll(it) }
                val adapterData = AdapterCheckinAdmin(listData)
                rvStudents.adapter=adapterData
                Log.d("DATA",data.toString())
                Log.d("DATA",status_code.toString())
            }

            override fun onFailure(call: Call<ArrayList<data_checkin>>, t: Throwable) {
                Log.e("ERROR", t.message.toString())
            }

        })
    }
}