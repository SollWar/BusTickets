package com.example.sollwar.bustickets

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.sollwar.bustickets.model.Bus
import com.example.sollwar.bustickets.model.City
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


    fun getBus(cityFrom: Int = 0, cityIn: Int = 0): LiveData<List<Bus>> = mainDao.getBus(cityFrom, cityIn)
    fun addBuses(buses: List<Bus>) {
        executor.execute {
            mainDao.addBuses(buses)
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