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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sollwar.bustickets.MainViewModel
import com.example.sollwar.bustickets.R
import com.example.sollwar.bustickets.model.BusRoute

class RouteFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var toolbar: Toolbar

    private var busId = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_route, container, false)

        busId = arguments?.getInt("busId") as Int
        Log.d("SQL_Route", busId.toString())
        viewModel.getBusRoute(busId)
        recyclerView = view.findViewById(R.id.recycler_view)
        toolbar = view.findViewById(R.id.toolbar)
        recyclerView.adapter = RouteAdapter(listOf())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        toolbar.title = "${viewModel.cityNameOut} -> ${viewModel.cityNameIn}"

        return view
    }

    override fun onStart() {
        super.onStart()
        viewModel.busRouteListLiveDate.observe(
            viewLifecycleOwner,
            Observer { routes ->
                routes.let {
                    recyclerView.adapter = RouteAdapter(routes)
                    Log.d("SQL_Route", routes.toString())
                }
            }
        )
    }

    private inner class RouteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val icon: TextView = itemView.findViewById(R.id.icon)
        private val cityNameTextView: TextView = itemView.findViewById(R.id.city_name)
        private val stopsNameTextView: TextView = itemView.findViewById(R.id.stops_name)
        private val stopsTimeTextView: TextView = itemView.findViewById(R.id.stops_time)

        fun bind(busRoute: BusRoute, position: Int, size: Int) {
            when (position) {
                0 -> icon.text = "O"
                size - 1 -> icon.text = "O"
                else -> icon.text = " |"
            }
            if (busRoute.cityName == viewModel.cityNameIn) {
                icon.text = "-> ${icon.text}"
            }
            Log.d("SQL_Route", position.toString())
            cityNameTextView.text = busRoute.cityName
            stopsNameTextView.text = busRoute.stopName
            stopsTimeTextView.text = busRoute.routeTime
        }
    }

    private inner class RouteAdapter(private var busRouteList: List<BusRoute>) : RecyclerView.Adapter<RouteHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteHolder {
            val view = layoutInflater.inflate(R.layout.item_route, parent, false)
            return RouteHolder(view)
        }

        override fun onBindViewHolder(holder: RouteHolder, position: Int) {
            holder.apply {
                holder.bind(busRouteList[position], position, itemCount)
            }
        }

        override fun getItemCount(): Int = busRouteList.size

    }

    companion object {
        fun newInstance(busId: Int): RouteFragment {
            return RouteFragment().apply {
                arguments = Bundle().apply {
                    putInt("busId", busId)
                }
            }
        }
    }

}