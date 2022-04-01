package com.example.sollwar.bustickets.postgredb

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sollwar.bustickets.model.Bus
import com.example.sollwar.bustickets.model.City
import java.sql.ResultSet
import java.sql.Statement

class PostgreDao {

    private val db: PostgreDatabase = PostgreDatabase()
    private lateinit var st: Statement
    var status = false

    init {
        if (db.status) {
            st = db.st
            status = db.status
        }
    }

    public fun getAllBus(): LiveData<List<Bus>> {
        val buses: ArrayList<Bus> = arrayListOf()
        val mutableLiveData: MutableLiveData<List<Bus>> = MutableLiveData()
        val query = "Select * From bus"
        val thread = Thread(Runnable {
            kotlin.run {
                try {
                    val rs: ResultSet = st.executeQuery(query)
                    while (rs.next()) {
                        Log.d("SQL", "${rs.getString(2)}.${rs.getString(3)}.${rs.getString(4)}.${rs.getString(5)}.${rs.getString(6)}")
                        buses.add((Bus(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6))))
                    }
                } catch (e: Exception) {
                    Log.d("SQL", e.toString())
                }
            }
        })
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            Log.d("SQL", e.toString())
        }
        Log.d("SQL", buses.toString())
        mutableLiveData.value = buses
        return mutableLiveData
    }

    public fun getCities(): LiveData<List<City>> {
        val cities: ArrayList<City> = arrayListOf()
        val mutableLiveData: MutableLiveData<List<City>> = MutableLiveData()
        val query = "Select * From city" // Запрос
        val thread = Thread(Runnable {
            kotlin.run {
                try {
                    val rs: ResultSet = st.executeQuery(query) // Результат запроса
                    while (rs.next()) { // Выводит результат, цифра это колонка Select *
                        Log.d("SQL", "${rs.getString("name")}.${rs.getString("description")}")
                        cities.add(City(rs.getString("id").toInt(), rs.getString("name"), rs.getString("description")))
                    }
                } catch (e: Exception) {
                    Log.d("SQL", e.toString())
                }
            }
        })
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            Log.d("SQL", e.toString())
        }
        mutableLiveData.value = cities
        return mutableLiveData
    }
}