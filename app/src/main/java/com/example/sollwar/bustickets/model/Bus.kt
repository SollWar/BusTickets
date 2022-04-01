package com.example.sollwar.bustickets.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity()
data class Bus(
    @PrimaryKey(autoGenerate = true)
    var busId: Int = 0,
    var name: String = ""
)
