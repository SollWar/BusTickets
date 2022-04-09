package com.example.sollwar.bustickets.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.sollwar.bustickets.model.*

@Dao
interface MainDao {
    @Query("SELECT * FROM city WHERE name LIKE :str LIMIT 3")
    suspend fun getCities(str: String): List<City>

    @Insert
    suspend fun addCity(city: City)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCities(cities: List<City>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBuses(buses: List<Bus>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRoutes(routes: List<Route>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStops(stops: List<Stop>)

    @Query("DELETE FROM city")
    suspend fun clearCityTable()

    @Query("SELECT t1.busId, t2.name, t1.stopFrom, t2.stopIn, t1.timeFrom, t2.timeIn, t2.price\n" +
            "FROM \n" +
            "(SELECT Route.busId as busId, Route.routeId as routeId, Route.time as timeFrom, Stop.name as stopFrom FROM Route, Stop, City WHERE Route.stopId = Stop.stopId and Stop.cityId = City.cityId and Stop.cityId = :cityFrom) t1 \n" +
            "INNER JOIN \n" +
            "(SELECT Route.busId  as busId, Route.time as timeIn, Bus.name as name, Route.price as price, Stop.name as stopIn FROM Route, Stop, City, Bus WHERE Bus.busId = Route.busId and Route.stopId = Stop.stopId and Stop.cityId = City.cityId and Stop.cityId = :cityIn) t2 \n" +
            "ON (t1.busId = t2.busId and  t2.timeIn > t1.timeFrom)" +
            "ORDER BY t1.timeFrom")
    suspend fun getBus(cityFrom: Int, cityIn: Int): List<BusOnRoute>

    @Query("SELECT *\n" +
            "FROM Bus\n" +
            "WHERE bus.busId = :busId")
    suspend fun getBusFromId(busId: Int): Bus

    @Query("SELECT Stop.name as stopName, City.name as cityName, Route.time as routeTime\n" +
            "FROM Bus, Route, Stop, City\n" +
            "WHERE Bus.busid = :busId and Bus.busid = Route.busid and Route.stopid = Stop.stopid and Stop.cityId = City.cityId\n" +
            "ORDER BY Route.time")
    suspend fun getBusRoute(busId: Int): List<BusRoute>

    @Query("SELECT * FROM route")
    suspend fun getAllRoute(): List<Route>
    @Query("SELECT * FROM stop")
    suspend fun getAllStop(): List<Stop>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBus(bus: Bus)

}