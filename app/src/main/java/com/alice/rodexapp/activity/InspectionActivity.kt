package com.alice.rodexapp.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alice.rodexapp.R

class InspectionActivity : AppCompatActivity() {

    private lateinit var etInspector: EditText
    private lateinit var etRoadName: EditText
    private lateinit var etRoadLength: EditText
    private lateinit var etRoadSection: EditText
    private lateinit var etRoadSurface: EditText
    private lateinit var btnSubmit: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspection)

        // Menghubungkan variabel dengan elemen XML
        etInspector = findViewById(R.id.etInspector)
        etRoadName = findViewById(R.id.etRoadName)
        etRoadLength = findViewById(R.id.etRoadLength)
        etRoadSection = findViewById(R.id.etRoadSection)
        etRoadSurface = findViewById(R.id.etRoadSurface)
        btnSubmit = findViewById(R.id.btnStart)

        // Set listener pada tombol submit
        btnSubmit.setOnClickListener {
            saveData()
            // Pindah ke InspectionResultActivity
            val intent = Intent(this, InspectionStartActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveData() {
        val inspector = etInspector.text.toString().trim()
        val roadName = etRoadName.text.toString().trim()
        val roadLengthStr = etRoadLength.text.toString().trim()
        val roadSectionStr = etRoadSection.text.toString().trim()
        val roadSurface = etRoadSurface.text.toString().trim()

        // Validasi input
        if (inspector.isEmpty() || roadName.isEmpty() || roadLengthStr.isEmpty() || roadSectionStr.isEmpty() || roadSurface.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Parse roadLength ke Integer
        val roadLength: Int
        try {
            roadLength = roadLengthStr.toInt()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Road Length must be a valid number", Toast.LENGTH_SHORT).show()
            return
        }

        // Parse roadSection ke Integer
        val roadSection: Int
        try {
            roadSection = roadSectionStr.toInt()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Road Section must be a valid number", Toast.LENGTH_SHORT).show()
            return
        }

        // Simpan data ke SharedPreferences (atau Anda dapat menyimpannya ke database atau lokasi penyimpanan lainnya)
        val sharedPreferences = getSharedPreferences("InspectionData", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("inspector", inspector)
        editor.putString("roadName", roadName)
        editor.putInt("roadLength", roadLength)
        editor.putInt("roadSection", roadSection)
        editor.putString("roadSurface", roadSurface)
        editor.apply()

        Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show()
    }
}
