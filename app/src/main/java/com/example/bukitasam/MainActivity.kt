package com.example.bukitasam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnQR = findViewById<FloatingActionButton>(R.id.fab_qr)

        btnQR.setOnClickListener{
            val intent= Intent(this, QRScanActivity::class.java)
            startActivity(intent)

        }
    }
}