package com.example.sollwar.bustickets.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BusOnRoute(
    @PrimaryKey
    var busId: Int = 0,
    var name: String = "",
    var cityFrom: Int = 0,
    var cityIn: Int = 0,
    var timeFrom: String = "",
    var timeIn: String = "",
    var price: Int = 0
)
