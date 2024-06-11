package com.alice.rodexapp.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alice.rodexapp.R
import com.alice.rodexapp.databinding.ActivityDetailBinding
import com.alice.rodexapp.databinding.ActivityReportDetailBinding
import com.alice.rodexapp.utils.Result
import com.alice.rodexapp.viewmodel.DetailViewModel
import com.alice.rodexapp.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide

class ReportDetailActivity : AppCompatActivity() {
    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(application)
    }
    private lateinit var binding: ActivityReportDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        setupAction()
    }

    private fun setupAction() {
        val userId = intent.getStringExtra(USER_ID)

        if (userId != null) {
            viewModel.getDetailStory(userId).observe(this) { result ->
                when (result) {
                    is Result.Loading -> {
                    }

                    is Result.Success -> {
                        binding.tvDesc.text = result.data.story.description
                        binding.tvDesc1.text = result.data.story.description
                        Glide.with(this)
                            .load(result.data.story.photoUrl)
                            .into(binding.ivPict)
                    }

                    is Result.Error -> {
                        Toast.makeText(
                            application,
                            "Error: ${result.error}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    companion object {
        const val USER_ID = "user id"
    }
}