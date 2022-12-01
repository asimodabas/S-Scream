package com.asimodabas.instagram_clone.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.view.isNotEmpty
import androidx.recyclerview.widget.RecyclerView
import com.asimodabas.instagram_clone.R
import com.asimodabas.instagram_clone.databinding.RecyclerRowBinding
import com.asimodabas.instagram_clone.model.Post
import com.asimodabas.instagram_clone.view.activity.MapsActivity
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class FeedRecyclerAdapter(
    val activity: Activity, private val postList: ArrayList<Post>, val mContext: Context
) :
    RecyclerView.Adapter<FeedRecyclerAdapter.PostHolder>() {

    class PostHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val currentData = postList[position]
        holder.binding.RecyclerEmailText.text = postList.get(position).email
        holder.binding.recyclerCommentText.text = postList.get(position).comment

        holder.binding.layout.setOnClickListener {
            val intent = Intent(activity, MapsActivity::class.java)
            intent.putExtra("selectedPost", postList.get(position))
            intent.putExtra("info", "old")
            activity.startActivity(intent)
        }

        holder.binding.imageView3.setOnClickListener {
            val popupMenus = PopupMenu(mContext, it)
            popupMenus.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.edit_row -> {
                        val v =
                            LayoutInflater.from(mContext).inflate(R.layout.edit_upl_layout, null)

                        val editRadioGender = v.findViewById<RadioGroup>(R.id.editRadioGender)
                        val editNote = v.findViewById<EditText>(R.id.editNote)

                        fun dataControl(): Boolean =
                            editNote.text.isNotEmpty() &&
                                    editRadioGender.isNotEmpty()

                        AlertDialog.Builder(mContext)
                            .setView(v)
                            .setPositiveButton("Güncelle") { dialog, _ ->
                                if (dataControl()) {

                                    //Edit Feed

                                    Toast.makeText(
                                        mContext,
                                        "Güncelleme Başarılı",
                                        Toast.LENGTH_LONG
                                    )
                                        .show()
                                    dialog.dismiss()
                                } else {
                                    Toast.makeText(
                                        mContext,
                                        "Tüm boşlukları doldurunuz",
                                        Toast.LENGTH_LONG
                                    )
                                        .show()
                                }
                            }.setNegativeButton("İptal") { dialog, _ ->
                                dialog.dismiss()
                            }.create()
                            .show()
                        true
                    }
                    R.id.delete_row -> {

                        val v = LayoutInflater.from(mContext)
                            .inflate(R.layout.delete_advert_layout, null)
                        AlertDialog.Builder(mContext)
                            .setView(v)
                            .setPositiveButton("Evet") { dialog, _ ->

                                //Delete Feed

                                Toast.makeText(mContext, "İlanınız Siliniyor", Toast.LENGTH_LONG)
                                    .show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("Hayır") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                        true
                    }
                    R.id.report_row -> {
                        val v =
                            LayoutInflater.from(mContext).inflate(R.layout.report_user_layout, null)
                        val reportEditText = v.findViewById<EditText>(R.id.reportEditText)

                        AlertDialog.Builder(mContext)
                            .setView(v)
                            .setPositiveButton("Şikayet Et") { dialog, _ ->

                                if (reportEditText.text.isNotEmpty()) {
                                    Toast.makeText(
                                        mContext,
                                        "Şikayet Başarılı...",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {

                                    Toast.makeText(
                                        mContext,
                                        "Lütfen şikayet mesajı giriniz.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                                dialog.dismiss()
                            }.setNegativeButton("İptal") { dialog, _ ->
                                dialog.dismiss()
                            }.create()
                            .show()
                        true
                    }
                    else -> true
                }
            }

            popupMenus.inflate(R.menu.row_menu)
            if (currentData.searchUid.equals(FirebaseAuth.getInstance().uid)) {
                popupMenus.menu.findItem(R.id.delete_row).isVisible = true
                popupMenus.menu.findItem(R.id.edit_row).isVisible = true
                popupMenus.menu.findItem(R.id.report_row).isVisible = false
            } else {
                popupMenus.menu.findItem(R.id.delete_row).isVisible = false
                popupMenus.menu.findItem(R.id.edit_row).isVisible = false
            }
            popupMenus.show()
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