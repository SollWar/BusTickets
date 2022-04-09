package com.example.sollwar.bustickets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.sollwar.bustickets.fragment.*


class MainActivity : AppCompatActivity(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            addFragment(MainFragment.newInstance())
        }
    }

    override fun cityClick(cityName: String, citySelect: String) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.get_up, R.anim.get_invisible, R.anim.get_visible, R.anim.get_down)
            .addToBackStack(null)
            .replace(R.id.fragment_container, CitySelectorFragment.newInstance(cityName, citySelect))
            .commit()
    }

    override fun findBus() {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.get_up, R.anim.get_invisible, R.anim.get_visible, R.anim.get_down)
            .addToBackStack(null)
            .replace(R.id.fragment_container, BusSelectionFragment.newInstance())
            .commit()
    }

    override fun busRouteClick(busOnRoutePosition: Int) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, BusRouteFragment.newInstance(busOnRoutePosition))
            .commit()
    }

    override fun showBusRoute(busId: Int) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, RouteFragment.newInstance(busId))
            .commit()
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
            .setCustomAnimations(R.anim.get_up, R.anim.get_invisible, R.anim.get_visible, R.anim.get_down)
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}