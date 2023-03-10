package com.gmail.gabow95k.tmdb.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.gmail.gabow95k.tmdb.R
import com.gmail.gabow95k.tmdb.databinding.ActivityMainBinding
import com.gmail.gabow95k.tmdb.service.LocationService
import com.gmail.gabow95k.tmdb.setFragment
import com.gmail.gabow95k.tmdb.ui.map.MapsFragment
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

        if (hasLocationPermission()) {
            startServiceTrack()
        } else {
            requestPermission(1)
        }
    }

    private fun requestPermission(code: Int) {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        ActivityCompat.requestPermissions(this, permissions, code)
    }

    private fun hasLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
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
                if (hasLocationPermission()) {
                    openMaps()
                } else {
                    requestPermission(2)
                }
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

    private fun openMaps() {
        setFragment(
            this.supportFragmentManager,
            MapsFragment(),
            R.id.contentFragment
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startServiceTrack()
            } else {
                showMessageToPermission()
            }
        }
        if (requestCode == 2) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openMaps()
            } else {
                showMessageToPermission()
            }
        }
    }

    private fun showMessageToPermission() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.location_permission))
        builder.setMessage(getString(R.string.message_permission))
        builder.setPositiveButton(getString(R.string.oc)) { _, _ ->
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
        }
        builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
}