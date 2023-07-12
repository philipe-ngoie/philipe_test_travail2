package com.example.philipetesttravail.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.philipetesttravail.areFieldsNotEmpty
import com.example.philipetesttravail.connection.password.Password
import com.example.philipetesttravail.data.model.User
import com.example.philipetesttravail.databinding.ActivityDashModifyBinding
import com.example.philipetesttravail.heritage.BaseClass
import com.example.philipetesttravail.showToast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase

class DashModify : BaseClass() {
    private lateinit var binding: ActivityDashModifyBinding

    private lateinit var name:TextInputEditText
    private lateinit var email:TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var password2: TextInputEditText
    private var currentEmailText=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashModifyBinding.inflate(layoutInflater)
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
        binding.motOulier.setOnClickListener {
            val intent= Intent(this, Password::class.java)
            startActivity(intent)
        }
        binding.register.setOnClickListener {
            if (binding.root.areFieldsNotEmpty(applicationContext)){
                progessb(true)
                update()

              /*  if (password.text.toString()==password2.text.toString()) {


                    *//*binding.motPass2.error=null
                    binding.motPass1.error=null*//*
                }else{
                    val message="Les mots de passes ne correspondent pas"
                   *//* binding.motPass2.error=message
                    binding.motPass1.error=message*//*
                }*/
            }



        }
    }
    private fun update(){

        val name = name.text.toString().trim()
        val email1 = email.text.toString().trim()
        val password = password.text.toString().trim()
        val user=super.getCurrentUser()

        val updates = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .build()
        val credential = EmailAuthProvider.getCredential(currentEmailText,binding.passwordOldEditText.text.toString().trim()) // Current Login Credentials

        user?.reauthenticate(credential)?.addOnCompleteListener {
            if (it.isSuccessful) {
                showToast("reauthentification reussi")
                user.updateEmail(email1).addOnCompleteListener { task ->
                    if (task.isSuccessful) {


                                val user = User(name,email1,password)
                                FirebaseDatabase.getInstance().getReference("Users")
                                    .child(super.getUid())
                                    .setValue(user).addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            showToast("Les détails de l'utilisateur ont été mis à jour avec succès.")
                                            progessb(false)

                                        } else {
                                            progessb(false)
                                        }
                                    }






                    } else {
                        showToast("Échec de la mise à jour de l'e-mail.${task.exception}")
                        progessb(false)
                    }
                }
            } else {
                showToast(it.exception.toString())

                progessb(false)
            }
        }

    }
    private fun progessb(show: Boolean) {
        val loading=binding.progressBar2
        if (show) {
            loading.visibility = View.VISIBLE
            // loading.setBackgroundColor(Color.argb(100, 15, 2, 55))
        } else loading.visibility = View.INVISIBLE
    }}
