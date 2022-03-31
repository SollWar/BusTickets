package com.example.sollwar.bustickets.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Bus::class,
            parentColumns = arrayOf("busId"),
            childColumns = arrayOf("busId"),
            onDelete = ForeignKey.CASCADE),
        ForeignKey(
            entity = Stop::class,
            parentColumns = arrayOf("stopId"),
            childColumns = arrayOf("stopId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Route(
    @PrimaryKey(autoGenerate = true)
    var routeId: Int = 0,
    var busId: Int = 0,
    var stopId: Int = 0,
    var time: String = ""
)
