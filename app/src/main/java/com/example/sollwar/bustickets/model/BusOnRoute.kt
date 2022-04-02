package com.example.sollwar.bustickets.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class BusOnRoute(
    var busId: Int = 0,
    var name: String = "",
    var stopFrom: String = "",
    var stopIn: String = "",
    var timeFrom: String = "",
    var timeIn: String = "",
    var price: Int = 0
)
