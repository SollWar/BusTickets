package com.example.sollwar.bustickets.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sollwar.bustickets.City
import com.example.sollwar.bustickets.R
import com.example.sollwar.bustickets.navigator

private const val CITY_NAME = "CITY_NAME"
private const val CITY_SELECTION = "CITY_SELECTION"
private const val CITY_OUT_SELECTION = "CITY_OUT_SELECTION"
private const val CITY_IN_SELECTION = "CITY_IN_SELECTION"

class CitySelectorFragment : Fragment() {

    private lateinit var autoCompleteCity: EditText
    private lateinit var recyclerView: RecyclerView

    private lateinit var cities: MutableList<City>
    private lateinit var cityName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_city_selector, container, false)
        cityName = arguments?.getString(CITY_NAME) as String
        fillCity()
        autoCompleteCity = view.findViewById(R.id.autoComplete_city_out)
        autoCompleteCity.hint = cityName
        recyclerView = view.findViewById(R.id.city_list)
        recyclerView.adapter = CityAdapter(cities)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        autoCompleteCity.requestFocus()
        val imm: InputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        return view
    }

    fun fillCity() {
        cities = mutableListOf()
        cities.add(City("Унеча","Брянская обл., Унечский р-он"))
        cities.add(City("Дубна","Московская обл."))
        cities.add(City("Москва","Московская обл."))
        cities.add(City("Сочи","Краснодарский край."))
        cities.add(City("Самара","Самарская обл."))
    }

    private inner class CityHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private lateinit var city: City

        private val cityNameTextView: TextView = itemView.findViewById(R.id.city_name)
        private val cityDescriptionTextView: TextView = itemView.findViewById(R.id.city_description)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            autoCompleteCity.setText(city.name)
        }

        fun bind(city: City) {
            this.city = city
            cityNameTextView.text = this.city.name
            cityDescriptionTextView.text = this.city.description
        }
    }

    private inner class CityAdapter(private var cityList: List<City>) : RecyclerView.Adapter<CityHolder>() {

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

    }

    companion object {
        fun newInstance(cityName: String): CitySelectorFragment {
            return CitySelectorFragment().apply {
                arguments = Bundle().apply {
                    putString(CITY_NAME, cityName)
                }
            }
        }
    }
}