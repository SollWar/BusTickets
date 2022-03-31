package com.example.sollwar.bustickets

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class CityViewModel : ViewModel() {
    private val mainDBRepository = MainDBRepository.get()
    private val postgreRepository = PostgreRepository.get()

    lateinit var citiesListLiveData: LiveData<List<City>>

    init {
        if (postgreRepository.status) {
            citiesListLiveData = postgreRepository.getCities()
            mainDBRepository.clearTable()
            mainDBRepository.addCities(citiesListLiveData.value!!)
        } else {
            citiesListLiveData = mainDBRepository.getCities()
        }
    }

    fun addCity(city: City) {
        mainDBRepository.addCity(city)
    }

    var cityNameOut: String = "Откуда"
    var cityNameIn: String = "Куда"
}