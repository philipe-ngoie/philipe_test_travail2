package com.example.philipetesttravail.heritage

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

open class BaseClass : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }
    fun getCurrentUser(): FirebaseUser? {
        return auth().currentUser
    }
    fun auth(): FirebaseAuth {
        return Firebase.auth
    }
    fun getUid(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

}