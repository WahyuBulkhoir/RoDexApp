package com.alice.rodexapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.alice.rodexapp.R
import com.alice.rodexapp.fragment.CameraFragment
import com.alice.rodexapp.fragment.MapsFragment

class InspectionStartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspection_start)

        // Memuat fragment kamera
        loadFragment(CameraFragment(), R.id.fragment_container_camera)

        // Memuat fragment peta
        loadFragment(MapsFragment(), R.id.fragment_container_map)

    }

    private fun loadFragment(fragment: Fragment, containerId: Int) {
        supportFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .commit()
    }
}