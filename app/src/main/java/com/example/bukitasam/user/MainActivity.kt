package com.example.bukitasam.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.bukitasam.Admin.RegisterActivity
import com.example.bukitasam.Admin.UserActivity
import com.example.bukitasam.LoginActivity

import com.example.bukitasam.Models.data_checkin
import com.example.bukitasam.R
import com.example.bukitasam.adapter.AdapterCheckin
import com.example.bukitasam.retrofit.RetrofitInstance
import com.example.bukitasam.retrofit.SessionManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    lateinit var swipeRefreshLayout: SwipeRefreshLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getCheckinData()
        checkLogin()


        swipeRefreshLayout = findViewById(R.id.swipe)
        swipeRefreshLayout.setOnRefreshListener {
            getCheckinData()
            swipeRefreshLayout.isRefreshing=false
        }
    }



    private fun checkLogin(){
        if (sessionManager.isLogin() == false){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }




override fun onResume() {
 getCheckinData()
    super.onResume()
}


    private fun getCheckinData(){
        sessionManager= SessionManager(this)
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

//    fun clickLogout(view: View) {
//        prefManager.removeData()
//        val intent = Intent(this, LoginActivity::class.java)
//        startActivity(intent)
//        finish()
//    }



}