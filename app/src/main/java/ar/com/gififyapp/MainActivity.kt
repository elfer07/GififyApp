package ar.com.gififyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import ar.com.gififyapp.application.ToastHelper
import ar.com.gififyapp.application.showToast
import ar.com.gififyapp.core.observe
import ar.com.gififyapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    @Inject lateinit var toastHelper: ToastHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupActionBarWithNavController(this, navController)

        Log.d("TAGG", "onCreate: ready")

        toastHelper.toastMessages.observe(this){
            showToast(it)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("TAGG", "onResume: ready")
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}