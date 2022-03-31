package com.example.sollwar.bustickets.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sollwar.bustickets.City

@Database(entities = [City::class], version = 1)
abstract class MainDatabase : RoomDatabase() {
    abstract fun mainDao(): MainDao
}