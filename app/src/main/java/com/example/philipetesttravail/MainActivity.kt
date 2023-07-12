package com.example.philipetesttravail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.philipetesttravail.dashboard.DashBoard
import com.example.philipetesttravail.heritage.BaseClass
import com.example.philipetesttravail.ui.login.LoginActivity

class MainActivity : BaseClass() {

    override fun onStart() {
        super.onStart()

        val curretUser = super.getCurrentUser()
        if (curretUser == null) {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else {

            val intent = Intent(this, DashBoard::class.java)
            startActivity(intent)
        }
    }
}