package com.m4.notes.ui.activity
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import androidx.navigation.fragment.NavHostFragment
import com.m4.notes.R
import com.m4.notes.databinding.ActivityMainBinding
import com.m4.notes.utils.PreferenceHelper

private lateinit var binding: ActivityMainBinding
private val sharedPreferences = PreferenceHelper()
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences.unit(this)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcvMain) as NavHostFragment
        val navController = navHostFragment.navController
        if (sharedPreferences.showOnBoard){
            navController.navigate(R.id.onBoardFragment)
        } else {
            navController.popBackStack()
            if (sharedPreferences.isLoggedIn){
                navController.navigate(R.id.noteFragment)
            } else{
                navController.navigate(R.id.signInFragment)
            }
        }




    }
}