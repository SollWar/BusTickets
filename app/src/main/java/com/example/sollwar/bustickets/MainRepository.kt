package com.example.sollwar.bustickets

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.sollwar.bustickets.room.MainDatabase
import java.util.concurrent.Executors

private const val DATABASE_NAME = "main-database"

class MainDBRepository private constructor(context: Context){
    private val database: MainDatabase = Room.databaseBuilder(
        context.applicationContext,
        MainDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val mainDao = database.mainDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getCities(): LiveData<List<City>> = mainDao.getCities()
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
    fun clearTable() {
        executor.execute {
            mainDao.clearTable()
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