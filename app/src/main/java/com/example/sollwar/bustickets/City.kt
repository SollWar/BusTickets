package com.example.sollwar.bustickets

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class City(
    @PrimaryKey(autoGenerate = true)
    var cityId: Int = 0,
    var name: String = "",
    var description: String = ""
)
