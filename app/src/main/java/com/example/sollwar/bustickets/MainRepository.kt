package com.example.sollwar.bustickets

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Room
import com.example.sollwar.bustickets.model.*
import com.example.sollwar.bustickets.room.MainDatabase
import java.util.concurrent.Executors

private const val DATABASE_NAME = "main-database"

class MainDBRepository private constructor(context: Context){
    private val database: MainDatabase = Room.databaseBuilder(
        context.applicationContext,
        MainDatabase::class.java,
        DATABASE_NAME
    ).fallbackToDestructiveMigration().build()

    private val mainDao = database.mainDao()

    suspend fun getCities(str: String = "%"): List<City> = mainDao.getCities(str)
    suspend fun addCities(cities: List<City>) = mainDao.addCities(cities)
    suspend fun clearCityTable() = mainDao.clearCityTable()
    suspend fun addCity(city: City) = mainDao.addCity(city)


    suspend fun getBus(cityFrom: Int = 0, cityIn: Int = 0): List<BusOnRoute> = mainDao.getBus(cityFrom, cityIn)
    suspend fun getAllRoute(): List<Route> = mainDao.getAllRoute()
    suspend fun getAllStop(): List<Stop> = mainDao.getAllStop()
    suspend fun addBuses(buses: List<Bus>) = mainDao.addBuses(buses)
    suspend fun addRoutes(routes: List<Route>) = mainDao.addRoutes(routes)
    suspend fun addStops(stops: List<Stop>) = mainDao.addStops(stops)
    suspend fun addBus(bus: Bus) = mainDao.addBus(bus)
    suspend fun getBusRoute(busId: Int): List<BusRoute> = mainDao.getBusRoute(busId)
    suspend fun getBusFromId(busId: Int): Bus = mainDao.getBusFromId(busId)

    companion object {
        private var INSTANCE: MainDBRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = MainDBRepository(context)
            }
        }

        fun get(): MainDBRepository {
            return INSTANCE?: throw IllegalStateException("MainDBRepository must be initialized")
        }
    }
}