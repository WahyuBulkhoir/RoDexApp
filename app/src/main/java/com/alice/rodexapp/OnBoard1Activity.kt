package com.alice.rodexapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class OnBoard1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_board1)

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this, OnBoard2Activity::class.java)
            startActivity(intent)
            }
        }

}