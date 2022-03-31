package com.example.sollwar.bustickets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.sollwar.bustickets.fragment.CitySelectorFragment
import com.example.sollwar.bustickets.fragment.MainFragment
import com.example.sollwar.bustickets.postgredb.PostgreDatabase


class MainActivity : AppCompatActivity(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            supportActionBar?.hide()
            addFragment(MainFragment.newInstance())
        }
    }

    override fun cityClick(cityName: String, citySelect: String) {
        replaceFragment(CitySelectorFragment.newInstance(cityName, citySelect))
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