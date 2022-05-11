package com.example.bukitasam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.bukitasam.Models.data_checkin
import com.example.bukitasam.retrofit.RetrofitInstance
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {


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

override fun onResume() {
 getCheckinData()
    super.onResume()
}


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navigation,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.navigation_home->{
                Toast.makeText(this, "this is Home page", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.navigation_profil->{
                startActivity(Intent(this,MainActivity::class.java))
                true
            }
            R.id.navigation_logou->{
                startActivity(Intent(this,LoginActivity::class.java))
                true
            }
            else->super.onOptionsItemSelected(item)
        }
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