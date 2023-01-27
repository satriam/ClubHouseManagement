package com.example.bukitasam.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import cn.pedant.SweetAlert.SweetAlertDialog

import com.example.bukitasam.LoginActivity
import com.example.bukitasam.R
import com.example.bukitasam.retrofit.SessionManager
import java.util.*

class DashboardUserActivity : AppCompatActivity() {

    var animTv: Animation? = null
    var hariIni: String? = null
    var dat: TextView? = null
    private lateinit var sessionManager: SessionManager
    private val TIME_INTERVAL = 2000
    private var mBackPressed: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_user)
        getpref()
        var dateNow = Calendar.getInstance().time
        var dat = findViewById<TextView>(R.id.tvDate)
        var t_salam = findViewById<TextView>(R.id.td_salam)
        val profil = findViewById<CardView>(R.id.cv_profil)
        val pengaduan = findViewById<CardView>(R.id.cv_pengaduan)
        val history = findViewById<CardView>(R.id.cv_history)
        val keluar = findViewById<CardView>(R.id.cv_keluar)

        history.setOnClickListener {
            val intent = Intent(this@DashboardUserActivity, MainActivity::class.java)
            startActivity(intent)
        }
        keluar.setOnClickListener{
            SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Apakah anda yakin untuk keluar?")
                .setConfirmText("Yakin")
                .setConfirmClickListener {
                    sessionManager.removeData()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .setCancelButton(
                    "Tidak"
                ) { sDialog -> sDialog.dismissWithAnimation() }
                .show()

        }
        pengaduan.setOnClickListener {
            val intent = Intent( this@DashboardUserActivity, QRScanActivity::class.java)
            startActivity(intent)
        }
        profil.setOnClickListener {
            val intent = Intent( this@DashboardUserActivity, edituser::class.java)
            startActivity(intent)
        }


        animTv = AnimationUtils.loadAnimation(this,R.anim.anim_tv)
        hariIni = DateFormat.format("EEEE", dateNow) as String
        dat.setAnimation(animTv)
        t_salam.startAnimation(animTv)


        if (hariIni.equals("sunday", ignoreCase = true)) {
            hariIni = "Minggu"
        } else if (hariIni.equals("monday", ignoreCase = true)) {
            hariIni = "Senin"
        } else if (hariIni.equals("tuesday", ignoreCase = true)) {
            hariIni = "Selasa"
        } else if (hariIni.equals("wednesday", ignoreCase = true)) {
            hariIni = "Rabu"
        } else if (hariIni.equals("thursday", ignoreCase = true)) {
            hariIni = "Kamis"
        } else if (hariIni.equals("friday", ignoreCase = true)) {
            hariIni = "Jumat"
        } else if (hariIni.equals("saturday", ignoreCase = true)) {
            hariIni = "Sabtu"
        }

        val tanggal = DateFormat.format("d", dateNow) as String
        val monthNumber = DateFormat.format("M", dateNow) as String
        val year = DateFormat.format("yyyy", dateNow) as String
        val month = monthNumber.toInt()
        var bulan: String? = null
        if (month == 1) {
            bulan = "Januari"
        } else if (month == 2) {
            bulan = "Februari"
        } else if (month == 3) {
            bulan = "Maret"
        } else if (month == 4) {
            bulan = "April"
        } else if (month == 5) {
            bulan = "Mei"
        } else if (month == 6) {
            bulan = "Juni"
        } else if (month == 7) {
            bulan = "Juli"
        } else if (month == 8) {
            bulan = "Agustus"
        } else if (month == 9) {
            bulan = "September"
        } else if (month == 10) {
            bulan = "Oktober"
        } else if (month == 11) {
            bulan = "November"
        } else if (month == 12) {
            bulan = "Desember"
        }
        val tgll = "$tanggal $bulan $year"
        var formatFix: String = hariIni + ", " + tgll
        dat.setText(formatFix)

        setSalam()
    }

    private fun setSalam(){
        val calendar = Calendar.getInstance()
        var t_salam = findViewById<TextView>(R.id.td_salam)
        val timeOfDay = calendar[Calendar.HOUR_OF_DAY]
        if (timeOfDay >= 0 && timeOfDay < 12) {
            t_salam.setText("Selamat Pagi")
        } else if (timeOfDay >= 12 && timeOfDay < 15) {
            t_salam.setText("Selamat Siang")
        } else if (timeOfDay >= 15 && timeOfDay < 18) {
            t_salam.setText("Selamat Sore")
        } else if (timeOfDay >= 18 && timeOfDay < 24) {
            t_salam.setText("Selamat Malam")
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

    private fun getpref(){
        sessionManager= SessionManager(this)
        val nama = sessionManager.getnama()
        val nopeg = sessionManager.getnopeg()
        val role = sessionManager.getrole()

        val t_nopeg = findViewById<TextView>(R.id.td_nik)
        val t_nama = findViewById<TextView>(R.id.td_nama)

        t_nopeg?.setText("" + nopeg)
        t_nama?.setText("" + nama +""+"("+role+")")
    }
}