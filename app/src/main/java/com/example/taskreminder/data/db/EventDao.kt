package com.example.taskreminder.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EventDao {

//    @Query("SELECT * FROM event WHERE time < :currentTime")
//    fun getNotActiveEvents(currentTime: String)
//
//    @Query("SELECT * FROM event WHERE time >= :currentTime")
//    fun getActiveEvents(currentTime: String)

    @Insert
    fun saveEvent(event: Event)

    @Delete
    fun deleteEvent(event: Event)
}