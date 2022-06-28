package com.example.bukitasam.Admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_delete_option,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.menu_delete->{
                deleteData()
                true
            }else->super.onOptionsItemSelected(item)
        }
    }
    private fun hapushistory() {
        val ettanggal: EditText = findViewById(R.id.et_tanggal)
        val etlokasi: EditText = findViewById(R.id.et_lokasi)
        val etnama: EditText = findViewById(R.id.et_nama)


        ettanggal.setText(intent.getStringExtra("tanggal"))
        etlokasi.setText(intent.getStringExtra("lokasi"))
        etnama.setText(intent.getStringExtra("nama"))
        val id = intent.getStringExtra("id")

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
                    Toast.makeText(
                        applicationContext,
                        "Data berhasil dihapus",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<data_checkin>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}