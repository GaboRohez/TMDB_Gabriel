package com.gmail.gabow95k.tmdb.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.gmail.gabow95k.tmdb.R
import com.gmail.gabow95k.tmdb.databinding.ActivityMainBinding
import com.gmail.gabow95k.tmdb.service.LocationService
import com.gmail.gabow95k.tmdb.setFragment
import com.gmail.gabow95k.tmdb.ui.movies.view.MoviesFragment
import com.gmail.gabow95k.tmdb.ui.profile.view.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)

        setUpBottomNavigation()

        setFragment(
            this.supportFragmentManager,
            ProfileFragment(),
            R.id.contentFragment
        )

        setUpEvents()

        startServiceTrack()
    }

    private fun startServiceTrack() {
        val intent = Intent(this, LocationService::class.java)
        startService(intent)
    }

    private fun setUpBottomNavigation() {
        bottomNavigationView = binding.bottomNavigation
        bottomNavigationView.selectedItemId = R.id.navigation_profile
    }

    private fun setUpEvents() {

        bottomNavigationView.setOnNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.navigation_profile -> {
                setFragment(
                    this.supportFragmentManager,
                    ProfileFragment(),
                    R.id.contentFragment
                )
                true
            }
            R.id.navigation_movies -> {
                setFragment(
                    this.supportFragmentManager,
                    MoviesFragment(),
                    R.id.contentFragment
                )
                true
            }
            R.id.navigation_map -> {
                true
            }
            R.id.navigation_album -> {
                true
            }
            else -> {
                true
            }
        }
    }

}