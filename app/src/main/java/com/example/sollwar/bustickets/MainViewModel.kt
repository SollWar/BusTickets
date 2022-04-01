package com.example.sollwar.bustickets

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sollwar.bustickets.model.Bus
import com.example.sollwar.bustickets.model.City

class MainViewModel : ViewModel() {
    private val mainDBRepository = MainDBRepository.get()
    private val postgreRepository = PostgreRepository.get()

    lateinit var citiesListLiveData: LiveData<List<City>>
    lateinit var busListLiveData: LiveData<List<Bus>>

    init {
        if (postgreRepository.status) {
            citiesListLiveData = postgreRepository.getCities()
            busListLiveData = postgreRepository.getAllBus()
            mainDBRepository.clearCityTable()
            mainDBRepository.addCities(citiesListLiveData.value!!)
            mainDBRepository.addBuses(busListLiveData.value!!)
        } else {
            citiesListLiveData = mainDBRepository.getCities()
            busListLiveData = mainDBRepository.getBus()
        }
    }


    fun refreshBuses(cityFrom: Int, cityIn: Int) {
        busListLiveData = mainDBRepository.getBus(cityFrom, cityIn)
    }
    fun refreshCities(filter: String = "") {
        val str = "$filter%"
        citiesListLiveData = mainDBRepository.getCities(str)
    }

    fun addCity(city: City) {
        mainDBRepository.addCity(city)
    }

    var cityIdFrom: Int = 0
    var cityIdIn: Int = 0
    var cityNameOut: String = "Откуда"
    var cityNameIn: String = "Куда"
}