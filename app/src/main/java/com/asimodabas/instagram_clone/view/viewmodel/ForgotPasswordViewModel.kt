package com.asimodabas.instagram_clone.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgotPasswordViewModel : ViewModel() {

    private val auth = Firebase.auth
    val errorMessage = MutableLiveData<String>()
    val success = MutableLiveData<Boolean>()

    fun forgotPassword(email: String) {
        forgotPasswordFirebase(email)
    }

    private fun forgotPasswordFirebase(email: String) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { process ->
            if (process.isSuccessful) {
                success.value = true
            }
        }.addOnFailureListener { error ->
            errorMessage.value = error.localizedMessage
        }
    }
}