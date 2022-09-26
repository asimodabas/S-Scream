package com.asimodabas.instagram_clone.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asimodabas.instagram_clone.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.imageviewInsan.setOnClickListener {
            insan()
        }
        binding.imageviewHayvan.setOnClickListener {
            hayvan()
        }
    }

    fun insan() {
        val intent = Intent(this, FeedActivity::class.java)
        intent.putExtra("insanmi", true)
        startActivity(intent)
    }

    fun hayvan() {
        val intent = Intent(this, FeedActivity::class.java)
        intent.putExtra("insanmi", false)
        startActivity(intent)
    }
}