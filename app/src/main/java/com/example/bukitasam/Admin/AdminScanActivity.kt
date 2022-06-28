package com.example.bukitasam.Admin

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.bukitasam.Models.data_checkin
import com.example.bukitasam.R
import com.example.bukitasam.retrofit.RetrofitInstance
import com.example.bukitasam.user.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminScanActivity : AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_scan)

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)==
            PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),123)
        }else{
            startScanning()
        }
    }

    private fun startScanning(){
        val apiClient = RetrofitInstance.Create(this)
        val scannerView: CodeScannerView =findViewById(R.id.scanner_view)
        codeScanner= CodeScanner(this,scannerView)
        codeScanner.camera=CodeScanner.CAMERA_BACK
        codeScanner.formats=CodeScanner.ALL_FORMATS

        codeScanner.autoFocusMode= AutoFocusMode.SAFE
        codeScanner.scanMode= ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled=true
        codeScanner.isFlashEnabled=false

        codeScanner.decodeCallback= DecodeCallback {
            runOnUiThread {
//                val hasil= it.text.toString()
                val sendData = apiClient.createcheckin(
                    it.text.toString()
                ).enqueue(object : Callback<data_checkin> {
                    override fun onResponse(call: Call<data_checkin>, response: Response<data_checkin>) {
                        val response =response.body()
                        if (response?.status==200 ) {

                            val intent = Intent(this@AdminScanActivity, MainAdminActivity::class.java).also {
                                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                            startActivity(intent)


                        } else {
                            Log.d("Debug", "")
                            Toast.makeText(this@AdminScanActivity, "Login failed!", Toast.LENGTH_SHORT).show()
                        }
                    }


                    override fun onFailure(call: Call<data_checkin>, t: Throwable) {
                        Toast.makeText(
                            this@AdminScanActivity,
                            t.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })
                Toast.makeText(this, "BERHASIL CHECKIN", Toast.LENGTH_SHORT).show()
            }
        }

        codeScanner.errorCallback= ErrorCallback {
            runOnUiThread {
                Toast.makeText(this, "Camera Initialization Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }

        scannerView.setOnClickListener{
            codeScanner.startPreview()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==123){
            if (grantResults[0]== PackageManager.PERMISSION_DENIED){
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show()
                startScanning()
            }else{
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(::codeScanner.isInitialized){
            codeScanner?.startPreview()
        }
    }

    override fun onPause() {
        if (::codeScanner.isInitialized){
            codeScanner?.releaseResources()
        }
        super.onPause()
    }
}