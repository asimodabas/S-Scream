package com.asimodabas.instagram_clone.view

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.asimodabas.instagram_clone.R
import com.asimodabas.instagram_clone.databinding.ActivityUploadBinding
import com.asimodabas.instagram_clone.model.Post
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class UploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    var selectedPicture: Uri? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val insanmi = intent.getBooleanExtra("insanmi", true)

        registerLauncher()

        sharedPreferences = this.getSharedPreferences(
            "com.asimodabas.instagram_clone.view",
            MODE_PRIVATE
        )

        auth = Firebase.auth
        firestore = Firebase.firestore
        storage = FirebaseStorage.getInstance()

        binding.uploadButton.setOnClickListener {
            upload(insanmi)
        }
        binding.imageView.setOnClickListener {
            selectImage(it)
        }
        binding.buttonMap.setOnClickListener {
            goMap()
        }
    }

    fun upload(insanmi: Boolean) {

        val uuid = UUID.randomUUID()
        val imageName = "$uuid.jpg"

        val reference = storage.reference
        val imageReference = reference.child("images/").child(imageName)

        if (selectedPicture != null) {

            imageReference.putFile(selectedPicture!!).addOnSuccessListener {
                //Url
                val uploadPictureReference = storage.reference.child("images").child(imageName)
                uploadPictureReference.downloadUrl.addOnSuccessListener {

                    val downloadUrl = it.toString()

                    if (auth.currentUser != null) {

                        val postMap = hashMapOf<String, Any>()

                        postMap.put("downloadUrl", downloadUrl)
                        postMap.put("userEmail", auth.currentUser!!.email!!)
                        postMap.put("comment", binding.commentText.text.toString())
                        postMap.put("date", Timestamp.now())
                        postMap.put("imageName", imageName)
                        postMap.put("latitude", sharedPreferences.getString("latitude", "0")!!)
                        postMap.put("longitude", sharedPreferences.getString("longitude", "0")!!)

                        postMap.put("name", binding.nameText.text.toString())
                        postMap.put("surname", binding.surnameText.text.toString())

                        if (insanmi) {
                            firestore.collection("insan").add(postMap).addOnSuccessListener {
                                finish()

                            }.addOnFailureListener {
                                Toast.makeText(
                                    this@UploadActivity,
                                    it.localizedMessage,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            firestore.collection("hayvan").add(postMap).addOnSuccessListener {
                                finish()

                            }.addOnFailureListener {
                                Toast.makeText(
                                    this@UploadActivity,
                                    it.localizedMessage,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun selectImage(view: View) {

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                Snackbar.make(view, "Permission needed", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Give permission") {
                        //Request permission
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }.show()
            } else {
                //Request permission
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        } else {
            val intentToGallery =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            //Activity result
            activityResultLauncher.launch(intentToGallery)
        }
    }

    private fun registerLauncher() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val intentFromResult = result.data
                    if (intentFromResult != null) {
                        selectedPicture = intentFromResult.data
                        selectedPicture?.let {
                            binding.imageView.setImageURI(it)
                        }
                    }
                }
            }
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
                if (result) {
                    //Permission granted
                    val intentToGallery =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    activityResultLauncher.launch(intentToGallery)
                } else {
                    //Permission denied
                    Toast.makeText(this, "Permission needed", Toast.LENGTH_LONG).show()
                }
            }
    }

    fun goMap() {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
        finish()
    }
}