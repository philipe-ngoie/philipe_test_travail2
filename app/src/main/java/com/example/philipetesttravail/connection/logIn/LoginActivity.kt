package com.example.philipetesttravail.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.example.philipetesttravail.areFieldsNotEmpty
import com.example.philipetesttravail.connection.password.Password
import com.example.philipetesttravail.dashboard.DashBoard
import com.example.philipetesttravail.data.model.User
import com.example.philipetesttravail.databinding.ActivitySignInBinding
import com.example.philipetesttravail.heritage.BaseClass
import com.example.philipetesttravail.showToast
import com.example.philipetesttravail.signUp.SignUp
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : BaseClass() {

    private lateinit var binding: ActivitySignInBinding

    private  lateinit var username:TextInputEditText
    private lateinit var password:TextInputEditText
    private lateinit var login:Button
    private lateinit var loading:ProgressBar
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)


         username = binding.username
         password = binding.password
         login = binding.login
         loading = binding.loading


        val usernameFromExtra=intent.getStringExtra("mail")
        val passwordFromExtra=intent.getStringExtra("password")
        username.setText(usernameFromExtra)
        password.setText(passwordFromExtra)
            login.setOnClickListener {

                val pass=binding.root.areFieldsNotEmpty(applicationContext)
                if (pass){

                    progessb(true)
                    try {
                        signinNow(username.text.toString(),password.text.toString())

                    }
                    catch (e: FirebaseAuthInvalidUserException){
                        when (e.errorCode) {
                            "ERROR_USER_DISABLED" -> {

                            }
                            "ERROR_USER_NOT_FOUND" -> {
                                showToast("vous n'etes pas encore enregistré")
                            }
                            "ERROR_USER_TOKEN_EXPIRED" -> {

                            }
                            "ERROR_INVALID_USER_TOKEN" -> {

                            }
                            else -> {
                            }
                        }

                    }catch (w: ClassNotFoundException){

                    }
                }
            }
        binding.register.setOnClickListener {
            val intent=Intent(this, SignUp::class.java)
            startActivity(intent)
        }
        binding.motOulier.setOnClickListener {
            val intent=Intent(this, Password::class.java)
            startActivity(intent)
        }
        }



    private fun progessb(show: Boolean) {
        if (show) {
            loading.setVisibility(View.VISIBLE)
           // loading.setBackgroundColor(Color.argb(100, 15, 2, 55))
        } else loading.setVisibility(View.INVISIBLE)
    }

    private fun signinNow(email: String,password:String){
            super.auth().signInWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener { task ->
                if(task.isSuccessful) {

                    if(super.getCurrentUser()?.isEmailVerified == true){
                        progessb(false)

                        showToast("Connexion avec succes")
                        val intent=Intent(this, DashBoard::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        showToast("Vous n'avez pas verifier votre adresse email")
                    }

                }else {
                    progessb(false)
                    showToast("Verifier vos information$. ${task.exception}")
                    //showToast(task.result.toString())
                }
            })


    }

}




