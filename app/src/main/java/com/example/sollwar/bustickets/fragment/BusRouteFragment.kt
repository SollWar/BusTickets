package com.example.sollwar.bustickets.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.sollwar.bustickets.MainViewModel
import com.example.sollwar.bustickets.R
import com.example.sollwar.bustickets.model.BusOnRoute
import com.example.sollwar.bustickets.navigator

class BusRouteFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var routeTitleTextView: TextView
    private lateinit var timeFromTextView: TextView
    private lateinit var timeInTextView: TextView
    private lateinit var placeFromTextView: TextView
    private lateinit var placeInTextView: TextView
    private lateinit var priceTextView: TextView
    private lateinit var busRouteTimeTextView: TextView
    private lateinit var busCarrierTextView: TextView
    private lateinit var busModelTextView: TextView

    private lateinit var toolbar: Toolbar
    private lateinit var route: TextView

    private var busOnRoutePosition = 0
    private var busOnRoute: BusOnRoute? = null
    private var busId = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bus_route, container, false)

        route = view.findViewById(R.id.bus_route)
        routeTitleTextView = view.findViewById(R.id.route_title)
        timeFromTextView = view.findViewById(R.id.time_from)
        timeInTextView = view.findViewById(R.id.time_in)
        placeFromTextView = view.findViewById(R.id.place_from)
        placeInTextView = view.findViewById(R.id.place_in)
        priceTextView = view.findViewById(R.id.price)
        toolbar = view.findViewById(R.id.toolbar)
        busRouteTimeTextView = view.findViewById(R.id.bus_route_time)
        busCarrierTextView = view.findViewById(R.id.bus_carrier)
        busModelTextView = view.findViewById(R.id.bus_model)
        toolbar.title = "Детали рейса"

        busOnRoutePosition = arguments?.getInt("position") as Int
        busOnRoute = viewModel.busOnRouteListLiveData.value?.get(busOnRoutePosition)
        routeTitleTextView.text = "${viewModel.cityNameOut} -> ${viewModel.cityNameIn}"
        timeFromTextView.text = busOnRoute?.timeFrom
        timeInTextView.text = busOnRoute?.timeIn
        placeFromTextView.text = busOnRoute?.stopFrom
        placeInTextView.text = busOnRoute?.stopIn
        busId = busOnRoute?.busId ?: 0
        Log.d("SQL_Route", busId.toString())
        priceTextView.text = "${busOnRoute?.price.toString()}р"
        viewModel.getBusFromId(busId)

        return view
    }

    override fun onStart() {
        super.onStart()
        route.setOnClickListener {
            navigator().showBusRoute(busId)
        }
        viewModel.busLiveData.observe(
            viewLifecycleOwner,
            Observer { bus ->
                bus?.let {
                    busRouteTimeTextView.text = timeFromRoute(busOnRoute!!.timeFrom, busOnRoute!!.timeIn)
                    busCarrierTextView.text = bus.carrier
                    busModelTextView.text = bus.busmodel
                }
            }
        )
    }

    private fun timeFromRoute(timeFrom: String, timeIn: String): String {
        val hoursFrom = timeFrom.substring(0, 2).toInt()
        val minutesFrom = timeFrom.substring(3, 5).toInt()
        val hoursIn = timeIn.substring(0, 2).toInt()
        val minutesIn = timeIn.substring(3, 5).toInt()
        val minutesFromSum = (hoursFrom * 60) + minutesFrom
        val minutesInSum = (hoursIn * 60) + minutesIn
        var minutesRoute = minutesInSum - minutesFromSum
        val hoursRoute = minutesRoute % 59
        minutesRoute -= (hoursRoute * 60)
        return "${hoursRoute}ч ${minutesRoute}м"
    }

    companion object {
        fun newInstance(busOnRoutePosition: Int): BusRouteFragment {
            return BusRouteFragment().apply {
                arguments = Bundle().apply {
                    putInt("position", busOnRoutePosition)
                }
            }
        }
    }
}