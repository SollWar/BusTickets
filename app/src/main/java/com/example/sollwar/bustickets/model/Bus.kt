package com.example.sollwar.bustickets.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = City::class,
            parentColumns = arrayOf("cityId"),
            childColumns = arrayOf("cityFrom"),
            onDelete = ForeignKey.CASCADE),
        ForeignKey(
            entity = City::class,
            parentColumns = arrayOf("cityId"),
            childColumns = arrayOf("cityIn"),
            onDelete = ForeignKey.CASCADE
        )])
data class Bus(
    @PrimaryKey(autoGenerate = true)
    var busId: Int = 0,
    var name: String = "",
    var cityFrom: Int = 0,
    var cityIn: Int = 0,
    var timeFrom: String = "",
    var timeIn: String = ""
)
