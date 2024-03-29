package com.asimodabas.instagram_clone.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.asimodabas.instagram_clone.R
import com.asimodabas.instagram_clone.adapter.FeedRecyclerAdapter
import com.asimodabas.instagram_clone.databinding.ActivityFeedBinding
import com.asimodabas.instagram_clone.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FeedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFeedBinding
    private lateinit var auth: FirebaseAuth
    private var postArrayList = arrayListOf<Post>()
    private lateinit var db: FirebaseFirestore
    private lateinit var feedAdapter: FeedRecyclerAdapter
    private var insanmi: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        insanmi = intent.getBooleanExtra("insanmi", true)

        auth = Firebase.auth
        db = Firebase.firestore

        getData(insanmi!!)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        feedAdapter = FeedRecyclerAdapter(this)
        binding.recyclerView.adapter = feedAdapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    feedAdapter.recyclerListDiffer.submitList(postArrayList.toList())
                } else {
                    feedAdapter.filter.filter(newText)
                }
                return false
            }
        })
    }

    private fun getData(insanmi: Boolean) {
        if (insanmi) {
            db.collection("insan").orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
                    } else {
                        if (value != null) {
                            if (!value.isEmpty) {

                                val documents = value.documents
                                postArrayList.clear()

                                for (document in documents) {
                                    val searchUid = document.get("searchUid") as String
                                    val comment = document.get("comment") as String
                                    val userEmail = document.get("userEmail") as String
                                    val downloadUrl = document.get("downloadUrl") as String
                                    val name = document.get("name") as String
                                    val surname = document.get("surname") as String
                                    val latitude = document.get("latitude") as String
                                    val longitude = document.get("longitude") as String

                                    val post = Post(
                                        searchUid,
                                        userEmail,
                                        comment,
                                        downloadUrl,
                                        name,
                                        surname,
                                        latitude,
                                        longitude
                                    )
                                    postArrayList.add(post)
                                    feedAdapter.recyclerListDiffer.submitList(postArrayList.toList())
                                }
                                feedAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
        } else {
            db.collection("hayvan").orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
                    } else {
                        if (value != null) {
                            if (!value.isEmpty) {

                                val documents = value.documents
                                postArrayList.clear()

                                for (document in documents) {
                                    val searchUid = document.get("searchUid") as String
                                    val comment = document.get("comment") as String
                                    val userEmail = document.get("userEmail") as String
                                    val downloadUrl = document.get("downloadUrl") as String
                                    val name = document.get("name") as String
                                    val surname = document.get("surname") as String
                                    val latitude = document.get("latitude") as String
                                    val longitude = document.get("longitude") as String

                                    val post = Post(
                                        searchUid,
                                        userEmail,
                                        comment,
                                        downloadUrl,
                                        name,
                                        surname,
                                        latitude,
                                        longitude
                                    )
                                    postArrayList.add(post)
                                }
                                feedAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.scream_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.add_post) {
            val intent = Intent(this, UploadActivity::class.java)
            intent.putExtra("insanmi", insanmi)
            startActivity(intent)
        } else if (item.itemId == R.id.signout) {
            auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}