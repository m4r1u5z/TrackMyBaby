package com.baddevelopergames.trackmybaby

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EventsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addEvent(event: Event)

    @Query("SELECT * FROM events ORDER BY timestamp DESC LIMIT 10")
    fun getEvents(): List<Event>
}