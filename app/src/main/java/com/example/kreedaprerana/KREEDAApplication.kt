package com.example.kreedaprerana

import android.app.Application
import com.example.kreedaprerana.data.AppRepository
import com.example.kreedaprerana.data.local.AppDatabase
import com.example.kreedaprerana.data.local.Student
import com.example.kreedaprerana.data.local.TrialEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class KREEDAApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { AppRepository(database.studentDao(), database.trialDao()) }

    override fun onCreate() {
        super.onCreate()
        seedData()
    }

    private fun seedData() {
        applicationScope.launch {
            if (database.studentDao().getStudentCountSync() == 0) {
                val students = listOf(
                    Student(name = "Arjun Singh", age = 14, sport = "Kabaddi", schoolClass = "8th A"),
                    Student(name = "Sania Mirza", age = 13, sport = "Athletics", schoolClass = "7th B"),
                    Student(name = "Rahul Dravid", age = 15, sport = "Long Jump", schoolClass = "9th C"),
                    Student(name = "Mary Kom", age = 14, sport = "High Jump", schoolClass = "8th B"),
                    Student(name = "Neeraj Chopra", age = 15, sport = "Athletics", schoolClass = "9th A")
                )
                students.forEach { student ->
                    val id = database.studentDao().insertStudent(student).toInt()
                    // Add some sample trials
                    if (student.sport == "Athletics") {
                        database.trialDao().insertTrial(TrialEntry(studentId = id, eventType = "100m Sprint", value = 13.5, unit = "sec"))
                        database.trialDao().insertTrial(TrialEntry(studentId = id, eventType = "100m Sprint", value = 12.8, unit = "sec"))
                    } else if (student.sport == "Long Jump") {
                        database.trialDao().insertTrial(TrialEntry(studentId = id, eventType = "Long Jump", value = 4.2, unit = "m"))
                        database.trialDao().insertTrial(TrialEntry(studentId = id, eventType = "Long Jump", value = 4.6, unit = "m"))
                    } else if (student.sport == "High Jump") {
                        database.trialDao().insertTrial(TrialEntry(studentId = id, eventType = "High Jump", value = 1.1, unit = "m"))
                        database.trialDao().insertTrial(TrialEntry(studentId = id, eventType = "High Jump", value = 1.25, unit = "m"))
                    }
                }
            }
        }
    }
}
