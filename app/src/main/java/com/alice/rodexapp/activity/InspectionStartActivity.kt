package com.alice.rodexapp.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.alice.rodexapp.R
import com.alice.rodexapp.fragment.CameraFragment
import com.alice.rodexapp.fragment.MapsFragment
import com.alice.rodexapp.fragment.ReportPageFragment

class InspectionStartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspection_start)

        if (allPermissionsGranted()) {
            loadFragments()
        } else {
            requestPermissions()
        }

        val btnEnd: Button = findViewById(R.id.btnEnd)
        btnEnd.setOnClickListener {
            loadFragment(ReportPageFragment(), R.id.fragment_container_camera)
        }
    }

    private fun allPermissionsGranted() = ContextCompat.checkSelfPermission(
        this, Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    private fun requestPermissions() {
        requestPermissionsLauncher.launch(arrayOf(Manifest.permission.CAMERA))
    }

    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions[Manifest.permission.CAMERA] == true) {
                loadFragments()
            } else {
                // Handle the case where the user denied the permission.
            }
        }

    private fun loadFragments() {
        // Load the camera fragment
        loadFragment(CameraFragment(), R.id.fragment_container_camera)

        // Load the map fragment
        loadFragment(MapsFragment(), R.id.fragment_container_map)
    }

    private fun loadFragment(fragment: Fragment, containerId: Int) {
        supportFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .commit()
    }
}