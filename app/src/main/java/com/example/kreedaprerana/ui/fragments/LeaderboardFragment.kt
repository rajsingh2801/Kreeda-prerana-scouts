package com.example.kreedaprerana.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kreedaprerana.KREEDAApplication
import com.example.kreedaprerana.databinding.FragmentLeaderboardBinding
import com.example.kreedaprerana.ui.adapters.LeaderboardAdapter
import com.example.kreedaprerana.ui.viewmodels.MainViewModel
import com.google.android.material.tabs.TabLayout

class LeaderboardFragment : Fragment() {

    private var _binding: FragmentLeaderboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels {
        MainViewModel.Factory((requireActivity().application as KREEDAApplication).repository)
    }

    private lateinit var leaderboardAdapter: LeaderboardAdapter
    private val events = listOf("100m Sprint", "Long Jump", "High Jump")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        leaderboardAdapter = LeaderboardAdapter()
        binding.rvLeaderboard.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = leaderboardAdapter
        }

        events.forEach { event ->
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(event))
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let { loadLeaderboard(it.text.toString()) }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        loadLeaderboard(events[0])
    }

    private fun loadLeaderboard(event: String) {
        viewModel.getLeaderboard(event).observe(viewLifecycleOwner) { entries ->
            leaderboardAdapter.submitList(entries)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
