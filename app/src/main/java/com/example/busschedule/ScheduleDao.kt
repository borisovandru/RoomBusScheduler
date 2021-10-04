package com.example.busschedule

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {
    //    Запрос SQL указывается как строка, переданная в аннотацию @Query
    @Query("SELECT * FROM schedule ORDER BY arrival_time ASC")
    fun getAll(): Flow<List<Schedule>>

    //    Вы можете ссылаться на значения Kotlin из запроса, поставив перед ним двоеточие.
    @Query("SELECT * FROM schedule WHERE stop_name = :stopName ORDER BY arrival_time ASC")
    fun getByStopName(stopName: String): Flow<List<Schedule>>
}