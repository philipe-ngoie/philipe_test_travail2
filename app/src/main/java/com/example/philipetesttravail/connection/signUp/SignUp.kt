package com.example.philipetesttravail.signUp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.philipetesttravail.R
import com.example.philipetesttravail.areFieldsNotEmpty
import com.example.philipetesttravail.dashboard.DashDelete
import com.example.philipetesttravail.data.model.User
import com.example.philipetesttravail.databinding.ActivitySignInBinding
import com.example.philipetesttravail.databinding.ActivitySignUpBinding
import com.example.philipetesttravail.heritage.BaseClass
import com.example.philipetesttravail.showToast
import com.example.philipetesttravail.ui.login.LoginActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase

class SignUp : BaseClass() {
    private lateinit var viewBinding: ActivitySignUpBinding
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        val userNameEdit=viewBinding.usernameR
        val passwordEdit1=viewBinding.passwordR
        val passwordEdit2=viewBinding.passwordR2
        val emailEdit=viewBinding.EmailR
        val signUpButton=viewBinding.register
        viewBinding.loginText.setOnClickListener {
            val intent= Intent(this, LoginActivity::class.java)
            //intent.putExtra("mail")
            startActivity(intent)
            finish()
        }
        signUpButton.setOnClickListener {
            if (viewBinding.root.areFieldsNotEmpty(applicationContext)) {
                if (viewBinding.passwordR.text.toString()==viewBinding.passwordR2.text.toString()) {
                    progessb(true)
                    signUpNow(
                        userNameEdit.text.toString(),
                        emailEdit.text.toString(),
                        passwordEdit2.text.toString()
                    )
                    viewBinding.motPass2.error=null
                    viewBinding.motPass1.error=null
                }else{
                    val message="Les mots de passes ne correspondent pas"
                    viewBinding.motPass2.error=message
                    viewBinding.motPass1.error=message
                }

            }
        }
    }
    private fun signUpNow(userName:String,email : String,password:String){
        try {
            super.auth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        super.getCurrentUser()!!.sendEmailVerification()?.addOnCompleteListener {
                            if (it.isSuccessful) {
                                showToast("verifiez votre boite mail pour confirmer votre compte")
                            } else {
                                showToast("impossible de lancer la verification de votre compte")
                        }
                    }
                        val user = User(userName,email,password)
                        val updates = UserProfileChangeRequest.Builder()
                            .setDisplayName(userName)
                            .build()
                        super.getCurrentUser()!!.updateProfile(updates).addOnCompleteListener { task1 ->
                            if (task1.isSuccessful) {
                                FirebaseDatabase.getInstance().getReference("Users")
                                    .child(super.getUid())
                                    .setValue(user).addOnCompleteListener { task ->
                                        if (task.isSuccessful) {

                                            showToast("Utilisateur ajouter avec succes")
                                            progessb(false)
                                            startActivityLogin(email, password)

                                        } else {
                                            progessb(false)
                                        }
                                    }
                            }else{
                                showToast(task1.exception.toString())
                                progessb(false)
                            }
                        }
                    } else {
                        showToast("Echec d'enregistrement")
                        progessb(false)
                    }
                })
        }catch (e: FirebaseAuthInvalidUserException){
            showToast(e.toString())
        }

    }
    private fun progessb(show: Boolean) {
        val loading=viewBinding.progressBar2
        if (show) {
            loading.visibility = View.VISIBLE
            // loading.setBackgroundColor(Color.argb(100, 15, 2, 55))
        } else loading.visibility = View.INVISIBLE
    }
    private fun startActivityLogin(email: String,password: String){
        val intent=Intent(this, LoginActivity::class.java)
        intent.putExtra("mail",email)
        intent.putExtra("password",password)
        startActivity(intent)
        finish()
    }
}