package com.example.sollwar.bustickets.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.sollwar.bustickets.MainViewModel
import com.example.sollwar.bustickets.R
import com.example.sollwar.bustickets.model.BusOnRoute

class BusRouteFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var routeTitleTextView: TextView
    private lateinit var timeFromTextView: TextView
    private lateinit var timeInTextView: TextView
    private lateinit var placeFromTextView: TextView
    private lateinit var placeInTextView: TextView
    private lateinit var priceTextView: TextView

    private var busOnRoutePosition = 0
    private var busOnRoute: BusOnRoute? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bus_route, container, false)

        routeTitleTextView = view.findViewById(R.id.route_title)
        timeFromTextView = view.findViewById(R.id.time_from)
        timeInTextView = view.findViewById(R.id.time_in)
        placeFromTextView = view.findViewById(R.id.place_from)
        placeInTextView = view.findViewById(R.id.place_in)
        priceTextView = view.findViewById(R.id.price)

        busOnRoutePosition = arguments?.getInt("position") as Int
        busOnRoute = viewModel.busOnRouteListLiveData.value?.get(busOnRoutePosition)
        routeTitleTextView.text = "${viewModel.cityNameOut} -> ${viewModel.cityNameIn}"
        timeFromTextView.text = busOnRoute?.timeFrom
        timeInTextView.text = busOnRoute?.timeIn
        placeFromTextView.text = busOnRoute?.stopFrom
        placeInTextView.text = busOnRoute?.stopIn
        priceTextView.text = "${busOnRoute?.price.toString()}р"


        return view
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