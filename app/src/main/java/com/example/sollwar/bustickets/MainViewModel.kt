package com.example.sollwar.bustickets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.sollwar.bustickets.model.*

class MainViewModel : ViewModel() {
    private val mainDBRepository = MainDBRepository.get()
    private val postgreRepository = PostgreRepository.get()

    lateinit var citiesListLiveData: LiveData<List<City>>
    lateinit var busListLiveData: LiveData<List<Bus>>
    lateinit var busOnRouteListLiveData: LiveData<List<BusOnRoute>>
    lateinit var routeListLiveData: LiveData<List<Route>>
    lateinit var stopListLiveData: LiveData<List<Stop>>

    init {
        if (postgreRepository.status) {
            citiesListLiveData = postgreRepository.getCities()
            busListLiveData = postgreRepository.getAllBus()
            stopListLiveData = postgreRepository.getAllStop()
            routeListLiveData = postgreRepository.getAllRoute()
            mainDBRepository.clearCityTable()
            mainDBRepository.addCities(citiesListLiveData.value!!)
            mainDBRepository.addBuses(busListLiveData.value!!)
            mainDBRepository.addStops(stopListLiveData.value!!)
            mainDBRepository.addRoutes(routeListLiveData.value!!)
        } else {
            citiesListLiveData = mainDBRepository.getCities()
            busOnRouteListLiveData = mainDBRepository.getBus()
            stopListLiveData = mainDBRepository.getAllStop()
            routeListLiveData = mainDBRepository.getAllRoute()
        }
    }


    fun refreshBuses(cityFrom: Int, cityIn: Int) {
        busOnRouteListLiveData = mainDBRepository.getBus(cityFrom, cityIn)
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