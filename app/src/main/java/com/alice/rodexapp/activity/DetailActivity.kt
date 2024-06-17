package com.alice.rodexapp.activity

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alice.rodexapp.R
import com.bumptech.glide.Glide
import com.alice.rodexapp.databinding.ActivityDetailBinding
import com.alice.rodexapp.viewmodel.DetailViewModel
import com.alice.rodexapp.viewmodel.ViewModelFactory
import com.alice.rodexapp.utils.Result
import com.google.android.material.appbar.MaterialToolbar

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
        setupToolbar()
        playAnimation()
    }
    private fun setupToolbar() {
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
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

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }

    companion object {
        const val USER_ID = "user id"
    }
}