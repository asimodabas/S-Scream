package com.asimodabas.instagram_clone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.asimodabas.instagram_clone.databinding.ActivityMainBinding
import com.asimodabas.instagram_clone.databinding.ActivityUploadBinding

class UploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        binding = ActivityUploadBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.uploadButton.setOnClickListener {
            upload()
        }
        binding.imageView.setOnClickListener {
            selectImage()
        }
    }

    fun upload(){

    }
    fun selectImage(){

    }
}