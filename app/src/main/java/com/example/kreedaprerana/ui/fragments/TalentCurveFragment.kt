package com.example.kreedaprerana.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.kreedaprerana.KREEDAApplication
import com.example.kreedaprerana.databinding.FragmentTalentCurveBinding
import com.example.kreedaprerana.ui.viewmodels.MainViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class TalentCurveFragment : Fragment() {

    private var _binding: FragmentTalentCurveBinding? = null
    private val binding get() = _binding!!

    private var studentId: Int = -1
    private val viewModel: MainViewModel by viewModels {
        MainViewModel.Factory((requireActivity().application as KREEDAApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTalentCurveBinding.inflate(inflater, container, false)
        studentId = arguments?.getInt("studentId") ?: -1
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getTrialsForStudent(studentId).observe(viewLifecycleOwner) { trials ->
            if (trials != null && trials.isNotEmpty()) {
                setupChart(trials)
            }
        }
    }

    private fun setupChart(trials: List<com.example.kreedaprerana.data.local.TrialEntry>) {
        val entriesByEvent = trials.groupBy { it.eventType }
        val dataSets = mutableListOf<LineDataSet>()

        val colors = listOf(Color.BLUE, Color.RED, Color.GREEN, Color.MAGENTA, Color.CYAN)
        var colorIndex = 0

        for ((event, eventTrials) in entriesByEvent) {
            val entries = eventTrials.sortedBy { it.recordedAt }.map {
                Entry(it.recordedAt.toFloat(), it.value.toFloat())
            }
            val dataSet = LineDataSet(entries, event)
            dataSet.color = colors[colorIndex % colors.size]
            dataSet.setCircleColor(colors[colorIndex % colors.size])
            dataSet.lineWidth = 2f
            dataSets.add(dataSet)
            colorIndex++
        }

        binding.lineChart.data = LineData(dataSets.toList())
        
        binding.lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.lineChart.xAxis.valueFormatter = object : ValueFormatter() {
            private val mFormat = SimpleDateFormat("dd/MM", Locale.getDefault())
            override fun getFormattedValue(value: Float): String {
                return mFormat.format(Date(value.toLong()))
            }
        }
        
        binding.lineChart.description.isEnabled = false
        binding.lineChart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
