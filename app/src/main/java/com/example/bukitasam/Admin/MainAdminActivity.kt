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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.bukitasam.*
import com.example.bukitasam.Models.data_checkin
import com.example.bukitasam.Models.data_checkin_admin
import com.example.bukitasam.adapter.AdapterCheckinAdmin
import com.example.bukitasam.retrofit.RetrofitInstance
import com.example.bukitasam.retrofit.SessionManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainAdminActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_admin)
        getcheckinData()
        checkLogin()


        //swipe down untuk refresh
        swipeRefreshLayout = findViewById(R.id.swipe)
        swipeRefreshLayout.setOnRefreshListener {
            getcheckinData()
            swipeRefreshLayout.isRefreshing=false
        }

    }

    //pengecekan sudah login atau belum
    private fun checkLogin(){
        if (sessionManager.isLogin() == false){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    //pemanggilan api untuk history checkin
    private fun getcheckinData(){
        sessionManager= SessionManager(this)
        val listData = ArrayList<data_checkin_admin>()
        val rvStudents: RecyclerView =findViewById(R.id.rv_checkin)
        rvStudents.setHasFixedSize(true)
        rvStudents.layoutManager= LinearLayoutManager(this)
        val apiClient = RetrofitInstance.Create(this)
        val callData = apiClient.getcheckinadmin()

        callData.enqueue(object : Callback<ArrayList<data_checkin_admin>> {
            override fun onResponse(
                call: Call<ArrayList<data_checkin_admin>>,
                response: Response<ArrayList<data_checkin_admin>>
            ) {
                val data = response.body()
                val status_code= response.code()


                data?.let { listData.addAll(it) }
                val adapterData = AdapterCheckinAdmin(listData)
                rvStudents.adapter=adapterData
                Log.e("DATA",data.toString())
                Log.e("DATA",status_code.toString())
            }

            override fun onFailure(call: Call<ArrayList<data_checkin_admin>>, t: Throwable) {
                Log.e("ERROR", t.message.toString())
            }

        })
    }
}