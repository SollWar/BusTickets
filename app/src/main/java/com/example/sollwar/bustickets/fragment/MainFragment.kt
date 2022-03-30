package com.example.sollwar.bustickets.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.sollwar.bustickets.City
import com.example.sollwar.bustickets.R
import com.example.sollwar.bustickets.navigator

private const val CITY_OUT_SELECTION = "CITY_OUT_SELECTION"
private const val CITY_IN_SELECTION = "CITY_IN_SELECTION"
private const val CITY_PLACEHOLDER = "CITY_PLACEHOLDER"
private const val CITY_KEY = "CITY_KEY"

class MainFragment : Fragment() {

    private lateinit var autoCompleteCityIn: Button
    private lateinit var autoCompleteCityOut: Button
    private lateinit var anim: Animation

    private lateinit var cityKey: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        autoCompleteCityIn = view.findViewById(R.id.button_city_in)
        autoCompleteCityOut = view.findViewById(R.id.button_city_out)
        anim = AnimationUtils.loadAnimation(requireContext(), R.anim.alpha_in)
        cityKey = arguments?.getString(CITY_KEY) as String
        if (cityKey == CITY_PLACEHOLDER) {
            autoCompleteCityOut.hint = "Дубна"
            autoCompleteCityIn.hint = "Москва"
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        autoCompleteCityOut.setOnClickListener {
            navigator().cityClick(autoCompleteCityOut.hint.toString(), CITY_OUT_SELECTION)
        }
        autoCompleteCityIn.setOnClickListener {
            navigator().cityClick(autoCompleteCityIn.hint.toString(), CITY_IN_SELECTION)
        }
    }

    companion object {
        fun newInstance(key: String): MainFragment {
            return MainFragment().apply {
                arguments = Bundle().apply {
                    putString(CITY_KEY, key)
                }
            }
        }
    }

}