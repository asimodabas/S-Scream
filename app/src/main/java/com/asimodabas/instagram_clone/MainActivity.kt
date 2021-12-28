package com.asimodabas.instagram_clone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.asimodabas.instagram_clone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.signInButton.setOnClickListener {
           signInClicked()
        }

        binding.signUpButton.setOnClickListener {
            signUpClicked()
        }
    }

    fun signInClicked(){

    }

    fun signUpClicked(){

    }
}