package com.example.philipetesttravail.dashboard

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.philipetesttravail.MainActivity
import com.example.philipetesttravail.databinding.ActivityDashDeleteBinding
import com.example.philipetesttravail.heritage.BaseClass
import com.example.philipetesttravail.showToast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.EmailAuthProvider

class DashDelete : BaseClass() {
    private lateinit var binding: ActivityDashDeleteBinding

    private lateinit var name:TextView
    private lateinit var email:TextView
    private lateinit var password: TextInputEditText
    private lateinit var password2: TextInputEditText
    private var currentEmailText=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        name = binding.usernameR
      //email = binding.EmailR
        email=binding.emailOldEditText
         //password2 = binding.passwordR2
        password=binding.passwordOldEditText


        val user = super.getCurrentUser()
        user?.let {
            name.setText(it.displayName)
            binding.emailOldEditText.setText(it.email)
            val photoUrl = it.photoUrl

            currentEmailText= it.email!!
            val emailVerified = it.isEmailVerified

            val uid = it.uid
        }

        binding.register.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Suprimer votre compte")
            builder.setMessage("Etes vous sur de supprimer votre compe?")
            builder.setPositiveButton("Yes") { dialog, which ->
                progessb(true)
                val credential = EmailAuthProvider.getCredential(
                    currentEmailText,
                    binding.passwordOldEditText.text.toString().trim()
                ) // Current Login Credentials

                user?.reauthenticate(credential)?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast("reauthentification reussi")
                        val firebaseUser = super.getCurrentUser()
                        firebaseUser?.delete()?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                runOnUiThread {
                                    super.auth().signOut()

                                    if (super.auth().currentUser != null) {
                                        super.auth().signOut()
                                    } else {
                                        progessb(false)
                                        showToast("Compte suprimer avec succes")
                                        val packageManager: PackageManager = applicationContext.packageManager
                                        val intent = packageManager.getLaunchIntentForPackage(applicationContext.packageName)
                                        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        //val intent = Intent(this, MainActivity::class.java)
                                        startActivity(intent)
                                    }
                                }



                            } else {
                                showToast(task.exception.toString())
                                progessb(false)
                            }
                        }
                    }else{
                        showToast(it.exception.toString())

                        progessb(false)
                    }
                }

                }
            builder.setNegativeButton("Non") { dialog, which ->
                dialog.dismiss()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

    }

    private fun progessb(show: Boolean) {
        val loading=binding.progressBar2
        if (show) {
            loading.visibility = View.VISIBLE
            // loading.setBackgroundColor(Color.argb(100, 15, 2, 55))
        } else loading.visibility = View.INVISIBLE
    }}
