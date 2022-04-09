package com.example.sollwar.bustickets

import androidx.fragment.app.Fragment

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {
    fun cityClick(cityName: String, citySelect: String)
    fun findBus()
    fun busRouteClick(busOnRoutePosition: Int)
    fun showBusRoute(busId: Int)
}