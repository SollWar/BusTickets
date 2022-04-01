package com.example.sollwar.bustickets.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.sollwar.bustickets.model.*

@Dao
interface MainDao {
    @Query("SELECT * FROM city WHERE name LIKE :str LIMIT 3")
    fun getCities(str: String): LiveData<List<City>>

    @Insert
    fun addCity(city: City)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCities(cities: List<City>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBuses(buses: List<Bus>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRoutes(routes: List<Route>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addStops(stops: List<Stop>)

    @Query("DELETE FROM city")
    fun clearCityTable()

    @Query("SELECT t1.busId, t2.name, t1.cityFrom, t2.cityIn, t1.timeFrom, t2.timeIn, t2.price\n" +
            "FROM \n" +
            "(SELECT Route.busId as busId, Route.routeId as routeId, Route.time as timeFrom, City.cityId as cityFrom FROM Route, Stop, City WHERE Route.stopId = Stop.stopId and Stop.cityId = City.cityId and Stop.cityId = :cityFrom) t1 \n" +
            "INNER JOIN \n" +
            "(SELECT Route.busId  as busId, Route.time as timeIn, Bus.name as name, City.cityId as cityIn, Route.price as price FROM Route, Stop, City, Bus WHERE Bus.busId = Route.busId and Route.stopId = Stop.stopId and Stop.cityId = City.cityId and Stop.cityId = :cityIn) t2 \n" +
            "ON (t1.busId = t2.busId)")
    fun getBus(cityFrom: Int, cityIn: Int): LiveData<List<BusOnRoute>>

    @Query("SELECT * FROM route")
    fun getAllRoute(): LiveData<List<Route>>
    @Query("SELECT * FROM stop")
    fun getAllStop(): LiveData<List<Stop>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBus(bus: Bus)

}