package com.alice.rodexapp.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.alice.rodexapp.R
import com.alice.rodexapp.databinding.ActivityAddStoryBinding
import com.alice.rodexapp.fragment.CameraFragment
import com.alice.rodexapp.fragment.CameraFragment.Companion.CAMERA_RESULT
import com.alice.rodexapp.utils.reduceFileImage
import com.alice.rodexapp.utils.uriToFile
import com.alice.rodexapp.viewmodel.AddStoryViewModel
import com.alice.rodexapp.viewmodel.ViewModelFactory
import com.alice.rodexapp.utils.Result // Ensure this import is correct

class AddReportActivity : AppCompatActivity() {
    private val viewModel by viewModels<AddStoryViewModel> {
        ViewModelFactory.getInstance(application)
    }

    private lateinit var binding: ActivityAddStoryBinding
    private var currentImageUri: Uri? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Toast.makeText(this, R.string.no_media_selected, Toast.LENGTH_SHORT).show()
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_RESULT) {
            currentImageUri = it.data?.getStringExtra(CameraFragment.EXTRA_CAMERA_IMAGE)?.toUri()
            showImage()
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, R.string.permission_granted, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_LONG).show()
            }
        }

    private val requestPermissionLocationLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                    uploadImageLocation()
                }

                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                    uploadImageLocation()
                }

                else -> {
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val smLocation = binding.smLocation
        var state = false

        if (!allPermissionGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.btnCamera.setOnClickListener {
            startCamera()
        }

        binding.btnGallery.setOnClickListener {
            startGallery()
        }

        binding.uploadButton.setOnClickListener {
            if (state) {
                uploadImageLocation()
            } else {
                uploadImage()
            }
        }

        smLocation.setOnCheckedChangeListener { _, isChecked ->
            state = isChecked
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun startCamera() {
        val intent = Intent(this, CameraFragment::class.java)
        launcherIntentCamera.launch(intent)
    }

    private fun allPermissionGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    private fun showImage() {
        currentImageUri?.let {
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            val description = binding.edDescription.text.toString()

            viewModel.uploadImage(imageFile, description).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is Result.Success -> {
                            Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()
                            binding.progressBar.visibility = View.GONE
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }

                        is Result.Error -> {
                            Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                }
            }
        } ?: showToast(getString(R.string.empty_image))
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun uploadImageLocation() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            val description = binding.edDescription.text.toString()

            if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
                checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
            ) {
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        viewModel.uploadImageWithLocation(
                            imageFile,
                            description,
                            location.latitude,
                            location.longitude
                        ).observe(this) { result ->
                            if (result != null) {
                                when (result) {
                                    is Result.Loading -> {
                                        binding.progressBar.visibility = View.VISIBLE
                                    }

                                    is Result.Success -> {
                                        Toast.makeText(
                                            this,
                                            result.data.message,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        binding.progressBar.visibility = View.GONE
                                        val intent = Intent(this, MainActivity::class.java)
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        startActivity(intent)
                                    }

                                    is Result.Error -> {
                                        Toast.makeText(
                                            this,
                                            result.error,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        binding.progressBar.visibility = View.GONE
                                    }
                                }
                            }
                        }
                    } else {
                        showToast(getString(R.string.location_not_found))
                    }
                }
            } else {
                requestPermissionLocationLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        } ?: showToast(getString(R.string.empty_image))
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}
