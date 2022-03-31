package com.example.sollwar.bustickets.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
    ForeignKey(
        entity = City::class,
        parentColumns = arrayOf("cityId"),
        childColumns = arrayOf("cityId"),
        onDelete = ForeignKey.CASCADE
    )
])
data class Stop(
    @PrimaryKey(autoGenerate = true)
    var stopId: Int = 0,
    var name: String = "",
    var cityId: Int = 0,
)
