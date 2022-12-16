package com.example.bukitasam.Admin

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.media.session.MediaButtonReceiver.handleIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bukitasam.adapter.AdapterUser
import com.example.bukitasam.Models.UserModel
import com.example.bukitasam.R
import com.example.bukitasam.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
                getcheckinData()
//
            }

    override fun onResume() {
                getcheckinData()
                super.onResume()
            }

    private fun getcheckinData(){
                val listData = ArrayList<UserModel>()
                val rvStudents: RecyclerView =findViewById(R.id.rv_user)
                rvStudents.setHasFixedSize(true)
                rvStudents.layoutManager= LinearLayoutManager(this)
                val apiClient = RetrofitInstance.Create(this)
                val callData = apiClient.getUser()

                callData.enqueue(object : Callback<ArrayList<UserModel>> {
                    override fun onResponse(
                        call: Call<ArrayList<UserModel>>,
                        response: Response<ArrayList<UserModel>>
                    ) {
                        val data = response.body()
                        val status_code= response.code()

                        data?.let { listData.addAll(it) }
                        val adapterData = AdapterUser(listData)
                        rvStudents.adapter=adapterData
                        Log.d("DATA",data.toString())
                        Log.d("DATA",status_code.toString())
                    }

                    override fun onFailure(call: Call<ArrayList<UserModel>>, t: Throwable) {
                        Log.e("ERROR", t.message.toString())
                    }

                })
            }
        }
