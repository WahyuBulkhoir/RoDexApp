package com.alice.rodexapp.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.alice.rodexapp.databinding.ActivityDetailBinding
import com.alice.rodexapp.viewmodel.DetailViewModel
import com.alice.rodexapp.viewmodel.ViewModelFactory
import com.alice.rodexapp.utils.Result

class DetailActivity : AppCompatActivity() {
    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(application)
    }
    private lateinit var binding: ActivityDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
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
                        binding.tvName.text = result.data.story.name
                        binding.tvDesc.text = result.data.story.description
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