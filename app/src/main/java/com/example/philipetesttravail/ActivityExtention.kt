package com.example.philipetesttravail

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun AppCompatActivity.showToast(text:String){
    Toast.makeText(applicationContext,text,  Toast.LENGTH_LONG).show()
}
fun TextInputEditText.verify() {
    val edit=this
    val lay=(edit.parent.parent as TextInputLayout)
    if (edit.text.isNullOrEmpty()) {
        lay.error = lay.hint
    }else{
        lay.error=null;
    }
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (edit.text.isNullOrEmpty()) {
                lay.error = lay.hint
            }else{
                if (lay.hint!!.contains("email",true)) {
                    if (Patterns.EMAIL_ADDRESS.matcher(edit.text).matches()) {
                        lay.error = null
                    }
                }else{lay.error=null}

            }

        }
    })
}
fun View.areFieldsNotEmpty(context: Context): Boolean {
    var isNotEmpty = true
    if (this is ViewGroup) {
        for (i in 0 until this.childCount) {
            val child = this.getChildAt(i)
            if (child is TextInputEditText) {

                val lay=child.parent.parent as TextInputLayout

                if (child.text.isNullOrEmpty()) {
                    child.verify()
                    isNotEmpty = false
                    break
                }else{
                    if (lay.hint!!.contains("email",true))
                        if (!Patterns.EMAIL_ADDRESS.matcher(child.text).matches()) {
                            lay.setError("Entrer une adresse email valide")
                            isNotEmpty = false
                            break
                        }else{
                            child.verify()
                        }
                }
            }else if(child is AutoCompleteTextView ){
                if (child.text.isNullOrEmpty()) {
                    isNotEmpty = false
                    break
                }
            } else if (child is ViewGroup) {
                isNotEmpty = child.areFieldsNotEmpty(context)
                if (!isNotEmpty) {
                    break
                }
            }
        }
    }
    return isNotEmpty
}