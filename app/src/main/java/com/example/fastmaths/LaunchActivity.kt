package com.example.fastmaths

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        supportActionBar?.hide()

        Handler().postDelayed({
            val toHome = Intent(this@LaunchActivity,HomeActivity::class.java)
            startActivity(toHome)
            finish()
        },3000)
    }
}