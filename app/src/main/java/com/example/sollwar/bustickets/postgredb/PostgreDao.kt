package com.example.sollwar.bustickets.postgredb

import android.util.Log
import com.example.sollwar.bustickets.model.Bus
import com.example.sollwar.bustickets.model.City
import com.example.sollwar.bustickets.model.Route
import com.example.sollwar.bustickets.model.Stop
import java.sql.ResultSet
import java.sql.Statement

class PostgreDao {

    private val db: PostgreDatabase = PostgreDatabase()
    private lateinit var st: Statement
    var status = false

    init {
        if (db.status) {
            st = db.st!!
            status = db.status
        }
    }

    fun getAllStop(): List<Stop> {
        val stops: ArrayList<Stop> = arrayListOf()
        val query = "Select * From stop"
        try {
            val rs: ResultSet = st.executeQuery(query)
            while (rs.next()) {
                stops.add(Stop(rs.getInt(1), rs.getString(2), rs.getInt(3)))
                Log.d("SQL", "${rs.getInt(1)}, ${rs.getString(2)}, ${rs.getInt(3)}")
            }
        } catch (e: Exception) {
            Log.d("SQL", e.toString())
        }
        return stops
    }

    fun getAllRoute(): List<Route> {
        val routes: ArrayList<Route> = arrayListOf()
        val query = "Select * From route"
        try {
            val rs: ResultSet = st.executeQuery(query)
            while (rs.next()) {
                routes.add((Route(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5))))
            }
        } catch (e: Exception) {
            Log.d("SQL", e.toString())
        }
        return routes
    }

    fun getAllBus(): List<Bus> {
        val buses: ArrayList<Bus> = arrayListOf()
        val query = "Select * From bus"
        try {
            val rs: ResultSet = st.executeQuery(query)
            while (rs.next()) {
                buses.add((Bus(rs.getInt(1), rs.getString(2))))
            }
        } catch (e: Exception) {
            Log.d("SQL", e.toString())
        }
        return buses
    }

    fun getCities(): List<City> {
        val cities: ArrayList<City> = arrayListOf()
        val query = "Select * From city" // Запрос
        try {
            val rs: ResultSet = st.executeQuery(query) // Результат запроса
            while (rs.next()) { // Выводит результат, цифра это колонка Select *
                Log.d("SQL", "${rs.getString("name")}.${rs.getString("description")}")
                cities.add(City(rs.getString("id").toInt(), rs.getString("name"), rs.getString("description")))
            }
        } catch (e: Exception) {
            Log.d("SQL", e.toString())
        }
        return cities
    }
}