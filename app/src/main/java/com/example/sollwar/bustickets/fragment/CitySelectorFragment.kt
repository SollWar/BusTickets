package com.example.sollwar.bustickets.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sollwar.bustickets.model.City
import com.example.sollwar.bustickets.MainViewModel
import com.example.sollwar.bustickets.R

private const val CITY_NAME = "CITY_NAME"
private const val CITY_SELECTION = "CITY_SELECTION"
private const val CITY_OUT_SELECTION = "CITY_OUT_SELECTION"
private const val CITY_IN_SELECTION = "CITY_IN_SELECTION"

class CitySelectorFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var autoCompleteCity: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CityAdapter

    private lateinit var cityName: String
    private lateinit var citySelect: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_city_selector, container, false)
        cityName = arguments?.getString(CITY_NAME) as String
        citySelect = arguments?.getString(CITY_SELECTION) as String
        autoCompleteCity = view.findViewById(R.id.autoComplete_city_out)
        autoCompleteCity.hint = cityName
        recyclerView = view.findViewById(R.id.city_list)
        adapter = CityAdapter(arrayListOf())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        autoCompleteCity.requestFocus()
        val imm = (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)!!
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 1)

        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onStart() {
        super.onStart()
        autoCompleteCity.doAfterTextChanged {
            viewModel.refreshCities(it.toString())
            viewModel.citiesListLiveData.observe(
                viewLifecycleOwner,
                Observer { cities ->
                    cities?.let {
                        adapter = CityAdapter(ArrayList(cities))
                        recyclerView.adapter = adapter
                    }
                }
            )
            //adapter.filter.filter(it)
        }
    }


    private inner class CityHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private lateinit var city: City

        private val cityNameTextView: TextView = itemView.findViewById(R.id.city_name)
        private val cityDescriptionTextView: TextView = itemView.findViewById(R.id.city_description)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            if (citySelect == CITY_OUT_SELECTION) {
                viewModel.cityNameOut = city.name
                viewModel.cityIdFrom = city.cityId
            }
            if (citySelect == CITY_IN_SELECTION) {
                viewModel.cityNameIn = city.name
                viewModel.cityIdIn = city.cityId
            }

            requireActivity().onBackPressed()
            val imm = (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)!!
            imm.hideSoftInputFromWindow(recyclerView.windowToken, 0)
        }

        fun bind(city: City) {
            this.city = city
            cityNameTextView.text = this.city.name
            cityDescriptionTextView.text = this.city.description
        }
    }

    private inner class CityAdapter(private var cityList: ArrayList<City>) : RecyclerView.Adapter<CityHolder>(), Filterable {

        private var cityListFull = ArrayList(cityList)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
            val view = layoutInflater.inflate(R.layout.item_autocomplete_city, parent, false)
            return CityHolder(view)
        }

        override fun onBindViewHolder(holder: CityHolder, position: Int) {
            holder.apply {
                holder.bind(cityList[position])
            }
        }

        override fun getItemCount(): Int = cityList.size

        override fun getFilter(): Filter {
            return object : Filter() {
                override fun performFiltering(constraint: CharSequence?): FilterResults {
                    val filteredList = arrayListOf<City>()

                    if (constraint == null || constraint.isEmpty()) {
                        filteredList.addAll(cityListFull)
                    } else {
                        val filterPattern = constraint.toString().lowercase().trim()

                        for (item in cityListFull) {
                            if (item.name.lowercase().contains(filterPattern)) {
                                filteredList.add(item)
                            }
                        }
                    }

                    val result = FilterResults()
                    result.values = filteredList
                    return result
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                    cityList.clear()
                    cityList.addAll(results?.values as ArrayList<City>)
                    notifyDataSetChanged()
                }

            }
        }

    }

    companion object {
        fun newInstance(cityName: String, citySelect: String): CitySelectorFragment {
            return CitySelectorFragment().apply {
                arguments = Bundle().apply {
                    putString(CITY_NAME, cityName)
                    putString(CITY_SELECTION, citySelect)
                }
            }
        }
    }
}