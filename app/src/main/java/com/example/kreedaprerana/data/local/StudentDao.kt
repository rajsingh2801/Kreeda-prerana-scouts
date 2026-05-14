package com.example.kreedaprerana.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StudentDao {
    @Insert
    suspend fun insertStudent(student: Student): Long

    @Insert
    suspend fun insertStudents(students: List<Student>)

    @Update
    suspend fun updateStudent(student: Student)

    @Delete
    suspend fun deleteStudent(student: Student)

    @Query("SELECT * FROM students ORDER BY createdAt DESC")
    fun getAllStudents(): LiveData<List<Student>>

    @Query("SELECT * FROM students WHERE id = :studentId")
    fun getStudentById(studentId: Int): LiveData<Student>

    @Query("SELECT * FROM students WHERE name LIKE '%' || :query || '%'")
    fun searchStudents(query: String): LiveData<List<Student>>

    @Query("SELECT COUNT(*) FROM students")
    fun getStudentCount(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM students")
    suspend fun getStudentCountSync(): Int
}
