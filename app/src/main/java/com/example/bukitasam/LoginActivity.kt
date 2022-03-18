package com.example.bukitasam


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailId = findViewById<EditText>(R.id.til_email)
        val password = findViewById<EditText>(R.id.til_password)
        val btn_students = findViewById<Button>(R.id.btn_login)
        var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        btn_students.setOnClickListener {
//            val intent= Intent(this, MainActivity::class.java)
//            startActivity(intent)

            if (emailId.text.toString().isEmpty()) {
                Toast.makeText(applicationContext, "enter email address", Toast.LENGTH_SHORT).show()
                error("Email Salah")
            } else {
                if (emailId.text.toString().trim { it <= ' ' }.matches(emailPattern.toRegex())) {
                    Toast.makeText(applicationContext, "valid email address", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(applicationContext, "Invalid email address", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }
    }
}
