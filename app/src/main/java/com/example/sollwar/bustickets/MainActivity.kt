package com.example.sollwar.bustickets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.sollwar.bustickets.fragment.CitySelectorFragment
import com.example.sollwar.bustickets.fragment.MainFragment

private const val CITY_OUT_SELECTION = "CITY_OUT_SELECTION"
private const val CITY_IN_SELECTION = "CITY_IN_SELECTION"
private const val CITY_PLACEHOLDER = "CITY_PLACEHOLDER"

class MainActivity : AppCompatActivity(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            supportActionBar?.hide()
            addFragment(MainFragment.newInstance(CITY_PLACEHOLDER))
        }
    }

    override fun cityClick(cityName: String, citySelect: String) {
        replaceFragment(CitySelectorFragment.newInstance(cityName))
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, fragment)
            .commit()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}