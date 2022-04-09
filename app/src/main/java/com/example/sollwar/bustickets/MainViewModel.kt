package com.example.sollwar.bustickets

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sollwar.bustickets.model.*
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {
    private val mainDBRepository = MainDBRepository.get()
    private val postgreRepository = PostgreRepository.get()

    val citiesListLiveData = MutableLiveData<List<City>>()
    val busOnRouteListLiveData = MutableLiveData<List<BusOnRoute>>()
    val busRouteListLiveDate = MutableLiveData<List<BusRoute>>()
    val busLiveData = MutableLiveData<Bus>()

    fun getBusRoute(busId: Int) {
        viewModelScope.launch {
            val routeResponse = viewModelScope.async(Dispatchers.IO) {
                return@async mainDBRepository.getBusRoute(busId)
            }
            busRouteListLiveDate.value = routeResponse.await()
        }
    }

    init {
        viewModelScope.launch {
            val statusConnection = viewModelScope.async(Dispatchers.IO) {
                return@async postgreRepository.testConnection()
            }
            if (statusConnection.await()) {
                mainDBRepository.clearCityTable()
                val cityResponse = viewModelScope.async(Dispatchers.IO) {
                    return@async postgreRepository.getCities()
                }
                mainDBRepository.addCities(cityResponse.await())
                val busResponse = viewModelScope.async(Dispatchers.IO) {
                    return@async postgreRepository.getAllBus()
                }
                mainDBRepository.addBuses(busResponse.await())
                val stopResponse = viewModelScope.async(Dispatchers.IO) {
                    return@async postgreRepository.getAllStop()
                }
                mainDBRepository.addStops(stopResponse.await())
                val routeResponse = viewModelScope.async(Dispatchers.IO) {
                    return@async postgreRepository.getAllRoute()
                }
                mainDBRepository.addRoutes(routeResponse.await())
                offline = false
                Log.d("SQL", "All Loaded!")
            } else {
                offline = true
                Log.d("SQL", "Offline Mode!")
            }
        }
    }

    fun refreshBuses(cityFrom: Int, cityIn: Int) {
        viewModelScope.launch {
            busOnRouteListLiveData.value = mainDBRepository.getBus(cityFrom, cityIn)
        }
    }
    fun refreshCities(filter: String = "") {
        viewModelScope.launch {
            val str = "$filter%"
            citiesListLiveData.value = mainDBRepository.getCities(str)
        }
    }
    fun getBusFromId(busId: Int) {
        viewModelScope.launch {
            val busResponse = viewModelScope.async(Dispatchers.IO) {
                return@async mainDBRepository.getBusFromId(busId)
            }
            busLiveData.value = busResponse.await()
        }

    }

    var offline = false
    var cityIdFrom: Int = 0
    var cityIdIn: Int = 0
    var cityNameOut: String = "Откуда"
    var cityNameIn: String = "Куда"
}