package com.foodloop.foodloopapps.ui.mainactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.foodloop.foodloopapps.R
import com.foodloop.foodloopapps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var activityBinding: ActivityMainBinding
    private lateinit var mController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)

        val navHostFragment: Fragment =
            supportFragmentManager.findFragmentById(R.id.nav_container) as NavHostFragment
        mController = navHostFragment.findNavController()

        val appBarConfiguration: AppBarConfiguration = AppBarConfiguration.Builder(
            R.id.menu_home,
            R.id.menu_camera,
            R.id.menu_mypost,
            R.id.menu_profil
        ).build()

        setupActionBarWithNavController(mController, appBarConfiguration)
        activityBinding.apply {
            navButton.setupWithNavController(mController)
        }
    }
}