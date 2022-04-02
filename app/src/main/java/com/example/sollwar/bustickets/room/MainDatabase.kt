package com.example.sollwar.bustickets.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sollwar.bustickets.model.*

@Database(entities = [City::class, Bus::class, Stop::class, Route::class], version = 8)
abstract class MainDatabase : RoomDatabase() {
    abstract fun mainDao(): MainDao
}