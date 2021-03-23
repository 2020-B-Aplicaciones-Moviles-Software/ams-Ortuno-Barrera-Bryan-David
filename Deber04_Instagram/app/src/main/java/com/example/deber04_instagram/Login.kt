package com.example.deber04_instagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val botonLogin = findViewById<Button>(R.id.login_btn)
        botonLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }
}