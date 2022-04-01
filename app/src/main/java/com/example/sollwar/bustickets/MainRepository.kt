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
    private val executor = Executors.newSingleThreadExecutor()

    fun getCities(str: String = "%"): LiveData<List<City>> = mainDao.getCities(str)
    fun addCities(cities: List<City>) {
        executor.execute {
            mainDao.addCities(cities)
        }
    }
    fun addCity(city: City) {
        executor.execute {
            mainDao.addCity(city)
        }
    }
    fun clearCityTable() {
        executor.execute {
            mainDao.clearCityTable()
        }
    }

    fun getBus(cityFrom: Int = 0, cityIn: Int = 0): LiveData<List<BusOnRoute>> = mainDao.getBus(cityFrom, cityIn)
    fun getAllRoute(): LiveData<List<Route>> = mainDao.getAllRoute()
    fun getAllStop(): LiveData<List<Stop>> = mainDao.getAllStop()
    fun addBuses(buses: List<Bus>) {
        executor.execute {
            mainDao.addBuses(buses)
        }
    }
    fun addRoutes(routes: List<Route>) {
        executor.execute {
            mainDao.addRoutes(routes)
        }
    }
    fun addStops(stops: List<Stop>) {
        executor.execute {
            mainDao.addStops(stops)
        }
    }
    fun addBus(bus: Bus) {
        executor.execute {
            mainDao.addBus(bus)
        }
    }

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