package com.example.sollwar.bustickets

import android.content.Context
import androidx.lifecycle.LiveData
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

    public fun getCities(): LiveData<List<City>> = dao.getCities()
    public fun getAllBus(): LiveData<List<Bus>> = dao.getAllBus()
    public fun getAllStop(): LiveData<List<Stop>> = dao.getAllStop()
    public fun getAllRoute(): LiveData<List<Route>> = dao.getAllRoute()


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