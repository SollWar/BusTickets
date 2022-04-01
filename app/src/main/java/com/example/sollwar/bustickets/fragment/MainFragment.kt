package com.example.sollwar.bustickets.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.sollwar.bustickets.*

private const val CITY_OUT_SELECTION = "CITY_OUT_SELECTION"
private const val CITY_IN_SELECTION = "CITY_IN_SELECTION"

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var autoCompleteCityIn: Button
    private lateinit var autoCompleteCityOut: Button
    private lateinit var buttonBusFind: Button
    private lateinit var anim: Animation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        autoCompleteCityIn = view.findViewById(R.id.button_city_in)
        autoCompleteCityOut = view.findViewById(R.id.button_city_out)
        buttonBusFind = view.findViewById(R.id.button_find_bus)
        anim = AnimationUtils.loadAnimation(requireContext(), R.anim.alpha_in)
        return view
    }

    override fun onResume() {
        super.onResume()
        autoCompleteCityOut.hint = viewModel.cityNameOut
        autoCompleteCityIn.hint = viewModel.cityNameIn
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        autoCompleteCityOut.setOnClickListener {
            navigator().cityClick(autoCompleteCityOut.hint.toString(), CITY_OUT_SELECTION)
        }
        autoCompleteCityIn.setOnClickListener {
            navigator().cityClick(autoCompleteCityIn.hint.toString(), CITY_IN_SELECTION)
        }
        buttonBusFind.setOnClickListener {
            navigator().findBus()
        }
    }

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}