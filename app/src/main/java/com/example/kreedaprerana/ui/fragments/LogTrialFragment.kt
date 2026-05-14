package com.example.kreedaprerana.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.kreedaprerana.KREEDAApplication
import com.example.kreedaprerana.data.local.TrialEntry
import com.example.kreedaprerana.databinding.FragmentLogTrialBinding
import com.example.kreedaprerana.ui.viewmodels.MainViewModel
import java.util.*

class LogTrialFragment : Fragment() {

    private var _binding: FragmentLogTrialBinding? = null
    private val binding get() = _binding!!

    private var studentId: Int = -1
    private val viewModel: MainViewModel by viewModels {
        MainViewModel.Factory((requireActivity().application as KREEDAApplication).repository)
    }

    private val eventTypes = listOf("100m Sprint", "Long Jump", "High Jump", "200m Sprint", "400m Sprint")
    
    private var handler = Handler(Looper.getMainLooper())
    private var startTime = 0L
    private var timeInMilliseconds = 0L
    private var timeSwapBuff = 0L
    private var updatedTime = 0L
    private var isRunning = false

    private val updateTimerThread = object : Runnable {
        override fun run() {
            timeInMilliseconds = SystemClock.elapsedRealtime() - startTime
            updatedTime = timeSwapBuff + timeInMilliseconds
            
            val secs = (updatedTime / 1000).toInt()
            val mins = secs / 60
            val milliseconds = (updatedTime % 1000) / 10
            
            binding.tvChronometer.text = String.format(Locale.getDefault(), "%02d:%02d.%02d", mins, secs % 60, milliseconds)
            handler.postDelayed(this, 10)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogTrialBinding.inflate(inflater, container, false)
        studentId = arguments?.getInt("studentId") ?: -1
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, eventTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerEvent.adapter = adapter

        binding.spinnerEvent.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val event = eventTypes[position]
                if (event.contains("Sprint")) {
                    binding.tvUnitLabel.text = "Unit: sec"
                } else {
                    binding.tvUnitLabel.text = "Unit: m"
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.btnStart.setOnClickListener {
            if (!isRunning) {
                startTime = SystemClock.elapsedRealtime()
                handler.postDelayed(updateTimerThread, 0)
                isRunning = true
            }
        }

        binding.btnStop.setOnClickListener {
            if (isRunning) {
                timeSwapBuff += timeInMilliseconds
                handler.removeCallbacks(updateTimerThread)
                isRunning = false
            }
        }

        binding.btnReset.setOnClickListener {
            startTime = 0L
            timeInMilliseconds = 0L
            timeSwapBuff = 0L
            updatedTime = 0L
            binding.tvChronometer.text = "00:00.00"
            if (isRunning) {
                handler.removeCallbacks(updateTimerThread)
                isRunning = false
            }
        }

        binding.btnUseTime.setOnClickListener {
            val secs = updatedTime / 1000.0
            binding.etValue.setText(String.format(Locale.getDefault(), "%.2f", secs))
        }

        binding.btnSaveTrial.setOnClickListener {
            saveTrial()
        }
    }

    private fun saveTrial() {
        val valueStr = binding.etValue.text.toString()
        if (valueStr.isBlank()) {
            Toast.makeText(requireContext(), "Please enter a value", Toast.LENGTH_SHORT).show()
            return
        }

        val event = binding.spinnerEvent.selectedItem.toString()
        val unit = if (event.contains("Sprint")) "sec" else "m"

        val trial = TrialEntry(
            studentId = studentId,
            eventType = event,
            value = valueStr.toDouble(),
            unit = unit
        )

        viewModel.insertTrial(trial)
        Toast.makeText(requireContext(), "Trial saved", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(updateTimerThread)
        _binding = null
    }
}
