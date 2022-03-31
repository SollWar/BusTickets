package com.example.sollwar.bustickets.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sollwar.bustickets.model.Bus
import com.example.sollwar.bustickets.model.City
import com.example.sollwar.bustickets.model.Route
import com.example.sollwar.bustickets.model.Stop

@Database(entities = [City::class, Bus::class, Stop::class, Route::class], version = 4)
abstract class MainDatabase : RoomDatabase() {
    abstract fun mainDao(): MainDao
}