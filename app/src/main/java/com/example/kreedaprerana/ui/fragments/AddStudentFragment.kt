package com.example.kreedaprerana.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.kreedaprerana.KREEDAApplication
import com.example.kreedaprerana.data.local.Student
import com.example.kreedaprerana.databinding.FragmentAddStudentBinding
import com.example.kreedaprerana.ui.viewmodels.MainViewModel

class AddStudentFragment : Fragment() {

    private var _binding: FragmentAddStudentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels {
        MainViewModel.Factory((requireActivity().application as KREEDAApplication).repository)
    }

    private val sports = listOf("Kabaddi", "Athletics", "Long Jump", "High Jump", "Other")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sports)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerSport.adapter = adapter

        binding.btnSave.setOnClickListener {
            saveStudent()
        }

        binding.btnBatchEntry.setOnClickListener {
            performBatchEntry()
        }
    }

    private fun saveStudent() {
        val name = binding.etName.text.toString()
        val ageStr = binding.etAge.text.toString()
        val schoolClass = binding.etClass.text.toString()
        val sport = binding.spinnerSport.selectedItem.toString()

        if (name.isBlank() || ageStr.isBlank() || schoolClass.isBlank()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val student = Student(
            name = name,
            age = ageStr.toInt(),
            schoolClass = schoolClass,
            sport = sport
        )

        viewModel.insertStudent(student)
        Toast.makeText(requireContext(), "Student saved", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    private fun performBatchEntry() {
        // Simple batch entry: adds 30 generic students for demonstration
        val batchStudents = mutableListOf<Student>()
        for (i in 1..30) {
            batchStudents.add(
                Student(
                    name = "Batch Student $i",
                    age = 12,
                    schoolClass = "6th",
                    sport = sports.random()
                )
            )
        }
        viewModel.insertStudents(batchStudents)
        Toast.makeText(requireContext(), "30 students added", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
