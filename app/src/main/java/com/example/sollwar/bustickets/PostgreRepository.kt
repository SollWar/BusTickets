package com.example.sollwar.bustickets

import android.content.Context
import com.example.sollwar.bustickets.model.Bus
import com.example.sollwar.bustickets.model.City
import com.example.sollwar.bustickets.model.Route
import com.example.sollwar.bustickets.model.Stop
import com.example.sollwar.bustickets.postgredb.PostgreDao

class PostgreRepository(context: Context) {

    private val dao: PostgreDao = PostgreDao()
    var status = false

    init {
        status = dao.status
    }

    fun getCities(): List<City> = dao.getCities()
    fun getAllBus(): List<Bus> = dao.getAllBus()
    fun getAllStop(): List<Stop> = dao.getAllStop()
    fun getAllRoute(): List<Route> = dao.getAllRoute()


    companion object {
        private var INSTANCE: PostgreRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = PostgreRepository(context.applicationContext)
            }
        }

        fun get(): PostgreRepository {
            return INSTANCE?: throw IllegalStateException("PostgreRepository must be initialized")
        }
    }
}