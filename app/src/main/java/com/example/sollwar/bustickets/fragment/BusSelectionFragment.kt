package com.example.sollwar.bustickets.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sollwar.bustickets.MainViewModel
import com.example.sollwar.bustickets.R
import com.example.sollwar.bustickets.model.Bus

private const val CITY_FROM = "CITY_FROM"
private const val CITY_IN = "CITY_IN"

class BusSelectionFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BusAdapter

    private lateinit var cityFrom: String
    private lateinit var cityIn: String
    private var cityIdFrom: Int = 0
    private var cityIdIn: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bus_list, container, false)

        cityIdFrom = viewModel.cityIdFrom
        cityIdIn = viewModel.cityIdIn
        cityFrom = viewModel.cityNameOut
        cityIn = viewModel.cityNameIn

        recyclerView = view.findViewById(R.id.bus_recycler_view)
        adapter = BusAdapter(listOf())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        Toast.makeText(requireContext(), "$cityIdFrom $cityFrom ->$cityIdIn $cityIn", Toast.LENGTH_SHORT).show()
        viewModel.refreshBuses(cityIdFrom, cityIdIn)
        viewModel.busListLiveData.observe(
            viewLifecycleOwner,
            Observer { buses ->
                buses?.let {
                    adapter = BusAdapter(buses)
                    Log.d("Bus", buses.toString())
                    recyclerView.adapter = adapter
                }
            }
        )

        return view
    }

    private inner class BusHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private lateinit var bus: Bus

        private val timeFrom: TextView = itemView.findViewById(R.id.time_from)
        private val timeIn: TextView = itemView.findViewById(R.id.time_in)
        private val placeFrom: TextView = itemView.findViewById(R.id.place_from)
        private val placeIn: TextView = itemView.findViewById(R.id.place_in)
        private val busName: TextView = itemView.findViewById(R.id.bus_name)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            //
        }


        fun bind(bus: Bus) {
            this.bus = bus
            busName.text = this.bus.name
            timeFrom.text = this.bus.timeFrom
            timeIn.text = this.bus.timeIn
            placeFrom.text = viewModel.cityNameOut
            placeIn.text = viewModel.cityNameIn
        }
    }

    private inner class BusAdapter(private var busList: List<Bus>) : RecyclerView.Adapter<BusHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusHolder {
            val view = layoutInflater.inflate(R.layout.item_bus, parent, false)
            return BusHolder(view)
        }

        override fun onBindViewHolder(holder: BusHolder, position: Int) {
            holder.apply {
                holder.bind(busList[position])
            }
        }

        override fun getItemCount(): Int = busList.size

    }

    companion object {
        fun newInstance(): BusSelectionFragment {
            return BusSelectionFragment()
        }
    }
}