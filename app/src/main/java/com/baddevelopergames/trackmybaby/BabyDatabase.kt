package com.baddevelopergames.trackmybaby

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Event::class], version = 1)
abstract class BabyDatabase : RoomDatabase() {
    abstract fun eventsDao(): EventsDao

    companion object {
        @Volatile
        private var INSTANCE: BabyDatabase? = null

        fun getInstance(context: Context): BabyDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                BabyDatabase::class.java,
                "baby.db"
            ).allowMainThreadQueries()
                .build()
    }
}