package com.example.sollwar.bustickets.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity()
data class City(
    @PrimaryKey(autoGenerate = true)
    var cityId: Int = 0,
    var name: String = "",
    var description: String = ""
)
