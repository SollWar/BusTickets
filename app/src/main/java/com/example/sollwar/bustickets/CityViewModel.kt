package com.example.sollwar.bustickets

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sollwar.bustickets.model.City

class CityViewModel : ViewModel() {
    private val mainDBRepository = MainDBRepository.get()
    private val postgreRepository = PostgreRepository.get()

    lateinit var citiesListLiveData: LiveData<List<City>>

    init {
        if (postgreRepository.status) {
            citiesListLiveData = postgreRepository.getCities()
            mainDBRepository.clearCityTable()
            mainDBRepository.addCities(citiesListLiveData.value!!)
        } else {
            citiesListLiveData = mainDBRepository.getCities("%")
        }
    }

    fun refreshCities(filter: String = "") {
        val str = "$filter%"
        citiesListLiveData = mainDBRepository.getCities(str)
    }

    fun addCity(city: City) {
        mainDBRepository.addCity(city)
    }

    var cityNameOut: String = "Откуда"
    var cityNameIn: String = "Куда"
}