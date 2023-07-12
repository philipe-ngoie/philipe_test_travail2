package com.example.philipetesttravail.connection.password

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.philipetesttravail.R
import com.example.philipetesttravail.databinding.ActivityDashModifyBinding
import com.example.philipetesttravail.databinding.ActivityPasswordBinding
import com.example.philipetesttravail.heritage.BaseClass
import com.example.philipetesttravail.showToast

class Password : BaseClass() {
    private lateinit var binding: ActivityPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        reinit()
    }
    private fun reinit(){
        val inputEmail =binding.enterMailP
        val btnReset = binding.loginP

        btnReset.setOnClickListener {
            val email = inputEmail.text.toString().trim()

            if (email.isNotEmpty()) {
                auth().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            showToast("message de reiitialisation envoyer")
                        }
                    }
            } else {
            }
        }

    }
}