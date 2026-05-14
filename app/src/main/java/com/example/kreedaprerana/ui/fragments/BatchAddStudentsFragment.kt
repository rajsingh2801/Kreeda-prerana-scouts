package com.example.kreedaprerana.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.kreedaprerana.KREEDAApplication
import com.example.kreedaprerana.data.local.Student
import com.example.kreedaprerana.databinding.FragmentBatchAddStudentsBinding
import com.example.kreedaprerana.ui.viewmodels.MainViewModel

class BatchAddStudentsFragment : Fragment() {

    private var _binding: FragmentBatchAddStudentsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels {
        MainViewModel.Factory((requireActivity().application as KREEDAApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBatchAddStudentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnImport.setOnClickListener {
            val namesText = binding.etNames.text.toString().trim()
            if (namesText.isEmpty()) {
                Toast.makeText(context, "Please enter at least one name.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val defaultSport = binding.etDefaultSport.text.toString().trim()
            val defaultClass = binding.etDefaultClass.text.toString().trim()
            val defaultAgeText = binding.etDefaultAge.text.toString().trim()
            val defaultAge = defaultAgeText.toIntOrNull() ?: 14

            val names = namesText.split("\n").filter { it.isNotBlank() }
            val students = names.map { name ->
                Student(
                    name = name.trim(),
                    age = defaultAge,
                    sport = defaultSport.ifEmpty { "Athletics" },
                    schoolClass = defaultClass.ifEmpty { "8th A" }
                )
            }

            viewModel.insertStudents(students)
            Toast.makeText(context, "Batch added successfully!", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
