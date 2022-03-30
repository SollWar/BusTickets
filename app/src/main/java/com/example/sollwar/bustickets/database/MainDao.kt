package com.example.sollwar.bustickets.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.sollwar.bustickets.City

@Dao
interface MainDao {
    @Query("SELECT * FROM city")
    fun getCities(): LiveData<List<City>>

    @Insert
    fun addCity(city: City)

}