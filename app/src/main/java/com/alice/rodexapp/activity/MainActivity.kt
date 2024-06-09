package com.alice.rodexapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alice.rodexapp.R
import com.alice.rodexapp.adapter.LoadingStateAdapter
import com.alice.rodexapp.adapter.StoryAdapter
import com.alice.rodexapp.databinding.ActivityMainBinding
import com.alice.rodexapp.fragment.SearchFragment
import com.alice.rodexapp.viewmodel.MainViewModel
import com.alice.rodexapp.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(application)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        showRecyclerList()
    }

    private fun setupAction() {
        viewModel.getSession().observe(this) { session ->
            if (!session.isLogin) {
                val intent = Intent(this, WelcomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                binding.progressBar.visibility = View.GONE
                getStoriesData()
            }
        }

        binding.searchBar.setOnClickListener {
            // Replace current fragment with SearchFragment
            replaceFragment(SearchFragment())
        }

        loadFragment(Fragment())
        binding.menu.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.inspection -> {
                    val intent = Intent(this, InspectionActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.searchBar -> {
                    hideActionBar()
                    loadFragment(SearchFragment())
                    true
                }
                R.id.report -> {
                    AlertDialog.Builder(this).apply {
                        setTitle(R.string.message)
                        setMessage(R.string.ask_logout)
                        setPositiveButton(R.string.yes) { _, _ ->
                            viewModel.logout()
                        }
                        setNegativeButton(R.string.no) { _, _ -> }
                        create()
                        show()
                    }
                    true
                }
                else -> false
            }
        }
    }
    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    private fun hideActionBar() {
        supportActionBar?.hide()
    }
    private fun showRecyclerList() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvStory.addItemDecoration(itemDecoration)
    }

    private fun getStoriesData() {
        val adapter = StoryAdapter(this)
        adapter.removeDivider(binding.rvStory)

        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        viewModel.getStories().observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            commit()
        }
    }
}
