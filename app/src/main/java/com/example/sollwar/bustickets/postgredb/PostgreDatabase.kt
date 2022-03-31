package com.example.sollwar.bustickets.postgredb

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sollwar.bustickets.City
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

class PostgreDatabase{

    lateinit var st: Statement
    var status = false

    private lateinit var connection: Connection

    init {
        val user = "Sollwar"
        val password = "548639909242aA"
        val url = "jdbc:postgresql://rc1b-eolhpryga634iefm.mdb.yandexcloud.net:6432/Z3RG_BD"
        val thread = Thread(Runnable {
            kotlin.run {
                try {
                    Class.forName("org.postgresql.Driver")
                    connection = DriverManager.getConnection(url, user, password)
                    st = connection.createStatement()
                    status = true
                    Log.d("SQL", "Connected: $status")
                } catch (e: Exception) {
                    status = false
                    Log.d("SQL", e.message.toString())
                    e.printStackTrace()
                }
            }
        })
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            Log.d("SQL", e.toString())
        }
    }
}