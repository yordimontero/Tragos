package com.circleappsstudio.tragos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)

        //Setear una flecha de back en el ActionBar.
        NavigationUI.setupActionBarWithNavController(this, navController)

    }

    //Función de la flecha de back en el ActionBar.
    override fun onSupportNavigateUp(): Boolean {
        //Toast.makeText(this, "OnBackPressed", Toast.LENGTH_SHORT).show()
        return navController.navigateUp()
    }

}