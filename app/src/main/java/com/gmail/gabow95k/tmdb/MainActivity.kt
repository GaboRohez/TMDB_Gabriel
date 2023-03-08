package com.gmail.gabow95k.tmdb

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.gmail.gabow95k.tmdb.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpBottomNavigation()

        setFragment(
            this.supportFragmentManager,
            ProfileFragment(),
            R.id.contentFragment
        )
    }

    private fun setUpBottomNavigation() {
        bottomNavigationView = binding.bottomNavigation
        bottomNavigationView.selectedItemId = R.id.navigation_profile
    }

    private fun setUpEvents() {
        supportFragmentManager.addOnBackStackChangedListener(this)

        bottomNavigationView.setOnNavigationItemSelectedListener(this)

    }

    override fun onBackStackChanged() {
        if (supportFragmentManager.backStackEntryCount > 0) {

            val tagName: String? =
                supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1).name

            when (supportFragmentManager.findFragmentByTag(tagName)) {
                is ProfileFragment -> {
                    setTitle(R.string.profile_menu)
                }
            }
        }

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