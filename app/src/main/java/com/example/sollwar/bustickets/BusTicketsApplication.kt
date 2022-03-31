package com.example.sollwar.bustickets

import android.app.Application

class BusTicketsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MainDBRepository.initialize(this)
        PostgreRepository.initialize(this)
    }
}