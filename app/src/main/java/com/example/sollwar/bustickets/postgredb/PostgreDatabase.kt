package com.example.sollwar.bustickets.postgredb

import android.util.Log
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

class PostgreDatabase{

    var st: Statement? = null
    var status = false

    private var connection: Connection? = null

    init {
        val auth = AuthDate()
        val thread = Thread(Runnable {
            kotlin.run {
                try {
                    connection = DriverManager.getConnection(auth.url, auth.user, auth.password)
                    st = connection!!.createStatement()
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