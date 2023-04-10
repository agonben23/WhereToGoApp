package com.example.wheredatingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        supportActionBar?.hide()

        Handler().postDelayed(
            {
                val intent = Intent(this@LauncherActivity,MainActivity::class.java)
                startActivity(intent)
            },
            3000
        )

    }
}