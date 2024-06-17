package com.alice.rodexapp.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.alice.rodexapp.R
import com.alice.rodexapp.adapter.StoryAdapter
import com.alice.rodexapp.databinding.FragmentHomeBaseBinding
import com.alice.rodexapp.model.UserModel
import kotlinx.coroutines.launch

class HomeBaseFragment : Fragment(), StoryAdapter.OnUserClickListener {

    private lateinit var binding: FragmentHomeBaseBinding
    private lateinit var storyAdapter: StoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storyAdapter.setOnUserClickListener(this)

        lifecycleScope.launch {
        }
    }

    override fun onUserClick(data: UserModel) {
        val fragment = HomeBaseFragment()
        val bundle = Bundle()
        fragment.arguments = bundle

        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}