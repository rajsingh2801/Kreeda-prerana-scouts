package com.example.kreedaprerana.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kreedaprerana.KREEDAApplication
import com.example.kreedaprerana.R
import com.example.kreedaprerana.databinding.FragmentStudentDetailBinding
import com.example.kreedaprerana.ui.adapters.TrialAdapter
import com.example.kreedaprerana.ui.viewmodels.MainViewModel
import com.google.android.material.chip.Chip

class StudentDetailFragment : Fragment() {

    private var _binding: FragmentStudentDetailBinding? = null
    private val binding get() = _binding!!

    private var studentId: Int = -1

    private val viewModel: MainViewModel by viewModels {
        MainViewModel.Factory((requireActivity().application as KREEDAApplication).repository)
    }

    private lateinit var trialAdapter: TrialAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentDetailBinding.inflate(inflater, container, false)
        studentId = arguments?.getInt("studentId") ?: -1
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trialAdapter = TrialAdapter()
        binding.rvStudentTrials.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = trialAdapter
        }

        viewModel.getStudentById(studentId).observe(viewLifecycleOwner) { student ->
            student?.let {
                binding.tvDetailName.text = it.name
                binding.tvDetailInfo.text = "Age: ${it.age} | Class: ${it.schoolClass} | Sport: ${it.sport}"
            }
        }

        viewModel.getTrialsForStudent(studentId).observe(viewLifecycleOwner) { trials ->
            trialAdapter.submitList(trials)
            updateBadges(trials)
            binding.tvEmptyTrials.visibility = if (trials.isNullOrEmpty()) View.VISIBLE else View.GONE
        }

        binding.fabLogTrial.setOnClickListener {
            val bundle = Bundle().apply { putInt("studentId", studentId) }
            findNavController().navigate(R.id.action_studentDetailFragment_to_logTrialFragment, bundle)
        }

        binding.btnTalentCurve.setOnClickListener {
            val bundle = Bundle().apply { putInt("studentId", studentId) }
            findNavController().navigate(R.id.action_studentDetailFragment_to_talentCurveFragment, bundle)
        }
    }

    private fun updateBadges(trials: List<com.example.kreedaprerana.data.local.TrialEntry>) {
        binding.layoutBadges.removeAllViews()
        
        val has100mBadge = trials.any { it.eventType == "100m Sprint" && it.value < 13.0 }
        val hasLongJumpBadge = trials.any { it.eventType == "Long Jump" && it.value > 4.5 }
        val hasHighJumpBadge = trials.any { it.eventType == "High Jump" && it.value > 1.2 }

        if (has100mBadge || hasLongJumpBadge || hasHighJumpBadge) {
            addBadge("District Level Ready")
        }
    }

    private fun addBadge(text: String) {
        val chip = Chip(requireContext())
        chip.text = text
        binding.layoutBadges.addView(chip)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
