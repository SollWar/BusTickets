package com.example.sollwar.bustickets

import android.util.Log
import androidx.lifecycle.ViewModel

class CityViewModel : ViewModel() {
    private val mainDBRepository = MainDBRepository.get()

    val citiesListLiveData = mainDBRepository.getCities()

    fun addCity(city: City) {
        mainDBRepository.addCity(city)
        Log.d("ViewModel", "City add try")
    }

    var cityNameOut: String = "Откуда"
    var cityNameIn: String = "Куда"
}