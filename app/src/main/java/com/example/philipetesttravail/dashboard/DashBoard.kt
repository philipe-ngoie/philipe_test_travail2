package com.example.philipetesttravail.dashboard

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.philipetesttravail.MainActivity
import com.example.philipetesttravail.R
import com.example.philipetesttravail.databinding.ActivityMainBinding
import com.example.philipetesttravail.databinding.DashbordBinding
import com.example.philipetesttravail.heritage.BaseClass
import com.example.philipetesttravail.showToast
import com.example.philipetesttravail.ui.login.LoginActivity

class DashBoard : BaseClass() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: DashbordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = DashbordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.modifier.setOnClickListener {
            val intent= Intent(this, DashModify::class.java)
            //intent.putExtra("mail")
            startActivity(intent)
        }

        binding.suprimer.setOnClickListener {
            val intent= Intent(this, DashDelete::class.java)
            //intent.putExtra("mail")
            startActivity(intent)
        }
        val user = super.getCurrentUser()
        user?.let {
            val name = it.displayName
            val email = it.email
            val photoUrl = it.photoUrl

            val emailVerified = it.isEmailVerified

            val uid = it.uid
        }

    }

    private fun getUserCredential(){


    }

}