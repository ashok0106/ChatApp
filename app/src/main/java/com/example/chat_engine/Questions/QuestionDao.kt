package com.example.chat_engine.Questions

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
@Dao

interface QuestionDao {

    @Query("SELECT * FROM questions")
    fun getAllQuestions(): List<Question>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(questions: List<Question>)

    @Query("SELECT * FROM questions")
    fun getAllQuestionsFlow(): Flow<List<Question>>
}