package com.example.kreedaprerana.data

import com.example.kreedaprerana.data.local.Student
import com.example.kreedaprerana.data.local.StudentDao
import com.example.kreedaprerana.data.local.TrialDao
import com.example.kreedaprerana.data.local.TrialEntry

class AppRepository(private val studentDao: StudentDao, private val trialDao: TrialDao) {
    val allStudents = studentDao.getAllStudents()
    val recentTrials = trialDao.getRecentTrials()
    val studentCount = studentDao.getStudentCount()
    val trialCount = trialDao.getTrialCount()

    suspend fun insertStudent(student: Student) = studentDao.insertStudent(student)
    suspend fun insertStudents(students: List<Student>) = studentDao.insertStudents(students)
    suspend fun insertTrial(trial: TrialEntry) = trialDao.insertTrial(trial)

    fun getStudentById(id: Int) = studentDao.getStudentById(id)
    fun getTrialsForStudent(studentId: Int) = trialDao.getTrialsForStudent(studentId)
    fun searchStudents(query: String) = studentDao.searchStudents(query)
    fun getLeaderboard(eventType: String) = trialDao.getLeaderboard(eventType)
}
