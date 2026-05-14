package com.example.kreedaprerana.ui.viewmodels

import androidx.lifecycle.*
import com.example.kreedaprerana.data.AppRepository
import com.example.kreedaprerana.data.local.Student
import com.example.kreedaprerana.data.local.TrialEntry
import kotlinx.coroutines.launch

class MainViewModel(private val repository: AppRepository) : ViewModel() {

    val allStudents = repository.allStudents
    val recentTrials = repository.recentTrials
    val studentCount = repository.studentCount

    fun insertStudent(student: Student) = viewModelScope.launch {
        repository.insertStudent(student)
    }

    fun insertStudents(students: List<Student>) = viewModelScope.launch {
        repository.insertStudents(students)
    }

    fun insertTrial(trial: TrialEntry) = viewModelScope.launch {
        repository.insertTrial(trial)
    }

    fun getStudentById(id: Int) = repository.getStudentById(id)
    fun getTrialsForStudent(studentId: Int) = repository.getTrialsForStudent(studentId)
    fun searchStudents(query: String) = repository.searchStudents(query)
    fun getLeaderboard(eventType: String) = repository.getLeaderboard(eventType)

    class Factory(private val repository: AppRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
