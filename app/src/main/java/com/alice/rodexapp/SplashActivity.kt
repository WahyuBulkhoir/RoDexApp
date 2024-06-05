package com.alice.rodexapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Thread {
            try {
                Thread.sleep(3000) // Delay of 3 seconds
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            startActivity(Intent(this, OnBoard1Activity::class.java))
            finish()
        }.start()
        }

}