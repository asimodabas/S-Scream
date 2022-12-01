package com.asimodabas.instagram_clone.view.activity

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
            human()
        }
        binding.imageviewHayvan.setOnClickListener {
            animal()
        }
    }

    fun human() {
        val intent = Intent(this, FeedActivity::class.java)
        intent.putExtra("insanmi", true)
        startActivity(intent)
    }

    fun animal() {
        val intent = Intent(this, FeedActivity::class.java)
        intent.putExtra("insanmi", false)
        startActivity(intent)
    }
}