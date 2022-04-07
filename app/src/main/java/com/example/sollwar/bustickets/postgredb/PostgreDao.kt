package com.example.sollwar.bustickets.postgredb

import android.util.Log
import com.example.sollwar.bustickets.model.Bus
import com.example.sollwar.bustickets.model.City
import com.example.sollwar.bustickets.model.Route
import com.example.sollwar.bustickets.model.Stop
import kotlinx.coroutines.delay
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

class PostgreDao {

    var status = false

    private suspend fun connection(): Connection? {
        val auth = AuthDate()
        var connection: Connection? = null
        try {
            connection = DriverManager.getConnection(auth.url, auth.user, auth.password)
            status = true
        } catch (e: Exception) {
            status = false
            Log.d("SQL", e.message.toString())
            e.printStackTrace()
            delay(5000)
            Log.d("SQL", "Retry connection")
            return connection()
        }
        return connection
    }

    suspend fun getAllStop(): List<Stop> {
        val connection = connection()
        val statement = connection!!.createStatement()
        val stops: ArrayList<Stop> = arrayListOf()
        val query = "Select * From stop"
        try {
            val rs: ResultSet = statement.executeQuery(query)
            while (rs.next()) {
                stops.add(Stop(rs.getInt(1), rs.getString(2), rs.getInt(3)))
            }
            Log.d("SQL", "Stops Loaded!")
        } catch (e: Exception) {
            Log.d("SQL", e.toString())
        }
        return stops
    }

    suspend fun getAllRoute(): List<Route> {
        val connection = connection()
        val statement = connection!!.createStatement()
        val routes: ArrayList<Route> = arrayListOf()
        val query = "Select * From route"
        try {
            val rs: ResultSet = statement.executeQuery(query)
            while (rs.next()) {
                routes.add((Route(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5))))
            }
            Log.d("SQL", "Routes Loaded!")
        } catch (e: Exception) {
            Log.d("SQL", e.toString())
        }
        return routes
    }

    suspend fun getAllBus(): List<Bus> {
        val connection = connection()
        val statement = connection!!.createStatement()
        val buses: ArrayList<Bus> = arrayListOf()
        val query = "Select * From bus"
        try {
            val rs: ResultSet = statement.executeQuery(query)
            while (rs.next()) {
                buses.add((Bus(rs.getInt(1), rs.getString(2))))
            }
            Log.d("SQL", "Busses Loaded!")
        } catch (e: Exception) {
            Log.d("SQL", e.toString())
        }
        return buses
    }

    suspend fun getCities(): List<City> {
        val connection = connection()
        val statement = connection!!.createStatement()
        val cities: ArrayList<City> = arrayListOf()
        val query = "Select * From city" // Запрос
        try {
            val rs: ResultSet = statement.executeQuery(query) // Результат запроса
            while (rs.next()) { // Выводит результат, цифра это колонка Select *
                cities.add(City(rs.getString("id").toInt(), rs.getString("name"), rs.getString("description")))
            }
            Log.d("SQL", "Cities Loaded!")
        } catch (e: Exception) {
            Log.d("SQL", e.toString())
        }
        return cities
    }
}