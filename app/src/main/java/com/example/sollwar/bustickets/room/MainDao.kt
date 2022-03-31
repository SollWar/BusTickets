package com.example.sollwar.bustickets.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.sollwar.bustickets.City

@Dao
interface MainDao {
    @Query("SELECT * FROM city")
    fun getCities(): LiveData<List<City>>

    @Insert
    fun addCity(city: City)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCities(cities: List<City>)

    @Query("Delete from city")
    fun clearTable()

}