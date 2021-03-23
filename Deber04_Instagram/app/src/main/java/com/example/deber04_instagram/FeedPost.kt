package com.example.deber04_instagram

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.widget.ImageView


data class FeedPost(
    val id: String,
    val username: String,
    val image: Int,
    val caption: String,
    val likes: String,
    val photo: Int
){
}