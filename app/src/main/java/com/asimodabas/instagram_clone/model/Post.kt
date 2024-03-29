package com.asimodabas.instagram_clone.model

import java.io.Serializable

data class Post(
    var searchUid: String,
    val email: String,
    val comment: String,
    val downloadUrl: String,
    val name: String,
    val surname: String,
    val latitude: String,
    val longitude: String
) : Serializable {

}