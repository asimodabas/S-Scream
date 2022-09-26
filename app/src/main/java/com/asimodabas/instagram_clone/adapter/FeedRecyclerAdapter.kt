package com.asimodabas.instagram_clone.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asimodabas.instagram_clone.databinding.RecyclerRowBinding
import com.asimodabas.instagram_clone.model.Post
import com.asimodabas.instagram_clone.view.MapsActivity
import com.squareup.picasso.Picasso

class FeedRecyclerAdapter(val activity: Activity, private val postList: ArrayList<Post>) :
    RecyclerView.Adapter<FeedRecyclerAdapter.PostHolder>() {

    class PostHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {

        holder.binding.RecyclerEmailText.text = postList.get(position).email
        holder.binding.recyclerCommentText.text = postList.get(position).comment

        holder.binding.layout.setOnClickListener {
            val intent = Intent(activity, MapsActivity::class.java)
            intent.putExtra("selectedPost", postList.get(position))
            intent.putExtra("info", "old")
            activity.startActivity(intent)

        }

        holder.binding.recyclerNameText.text = postList.get(position).name
        holder.binding.recyclerSurnameNameText2.text = postList.get(position).surname

        Picasso.get().load(postList.get(position).downloadUrl)
            .into(holder.binding.recyclerImageView2)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

}