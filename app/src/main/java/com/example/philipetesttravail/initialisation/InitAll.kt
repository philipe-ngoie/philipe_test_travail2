package com.example.philipetesttravail.initialisation

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase

class InitAll : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)


    }

}