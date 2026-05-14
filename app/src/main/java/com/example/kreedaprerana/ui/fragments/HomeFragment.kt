package com.example.kreedaprerana.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kreedaprerana.KREEDAApplication
import com.example.kreedaprerana.R
import com.example.kreedaprerana.databinding.FragmentHomeBinding
import com.example.kreedaprerana.ui.adapters.TrialAdapter
import com.example.kreedaprerana.ui.viewmodels.MainViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels {
        MainViewModel.Factory((requireActivity().application as KREEDAApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    private lateinit var trialAdapter: TrialAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trialAdapter = TrialAdapter()
        binding.rvRecentTrials.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = trialAdapter
        }

        viewModel.studentCount.observe(viewLifecycleOwner) { count ->
            binding.tvTotalStudents.text = count.toString()
        }

        viewModel.recentTrials.observe(viewLifecycleOwner) { trials ->
            trialAdapter.submitList(trials)
        }

        binding.fabAddStudent.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addStudentFragment)
        }

        binding.btnBatchAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_batchAddStudentsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
