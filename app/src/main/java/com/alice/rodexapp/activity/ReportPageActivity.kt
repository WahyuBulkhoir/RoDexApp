package com.alice.rodexapp.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.alice.rodexapp.R
import com.alice.rodexapp.fragment.MapsFragment

class ReportPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_page)

        // Retrieve data from Intent extras
        val inspector = intent.getStringExtra("inspector")
        val roadName = intent.getStringExtra("roadName")
        val roadLength = intent.getStringExtra("roadLength")
        val roadSection = intent.getStringExtra("roadSection")
        val roadSurface = intent.getStringExtra("roadSurface")

        // Initialize views
        val inspectorTextView = findViewById<TextView>(R.id.etInspector)
        val roadNameTextView = findViewById<TextView>(R.id.etRoadName)
        val roadLengthTextView = findViewById<TextView>(R.id.etRoadLength)
        val roadSectionTextView = findViewById<TextView>(R.id.etRoadSection)
        val roadSurfaceSpinner = findViewById<Spinner>(R.id.spRoadSurface)

        // Set text values to TextViews
        inspectorTextView.text = inspector
        roadNameTextView.text = roadName
        roadLengthTextView.text = roadLength
        roadSectionTextView.text = roadSection

        // Populate Spinner with road surface options
        val roadSurfaceOptions = resources.getStringArray(R.array.road_surface_options)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roadSurfaceOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        roadSurfaceSpinner.adapter = adapter

        // Set selected road surface in Spinner
        if (roadSurface != null) {
            val position = roadSurfaceOptions.indexOf(roadSurface)
            if (position != -1) {
                roadSurfaceSpinner.setSelection(position)
            }
        }

        // Disable Spinner and EditTexts
        roadSurfaceSpinner.isEnabled = false
        inspectorTextView.isEnabled = false
        roadNameTextView.isEnabled = false
        roadLengthTextView.isEnabled = false
        roadSectionTextView.isEnabled = false

        // Add the MapFragment to the fragment_container_map
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container_map, MapsFragment::class.java, null)
            }
        }
    }
}