package com.example.sollwar.bustickets.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.sollwar.bustickets.model.Bus
import com.example.sollwar.bustickets.model.City
import com.example.sollwar.bustickets.model.Route
import com.example.sollwar.bustickets.model.Stop

@Dao
interface MainDao {
    @Query("SELECT * FROM city WHERE name LIKE :str LIMIT 3")
    fun getCities(str: String): LiveData<List<City>>

    @Insert
    fun addCity(city: City)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCities(cities: List<City>)

    @Query("Delete from city")
    fun clearCityTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBus(bus: Bus)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRoute(route: Route)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addStop(stop: Stop)

}