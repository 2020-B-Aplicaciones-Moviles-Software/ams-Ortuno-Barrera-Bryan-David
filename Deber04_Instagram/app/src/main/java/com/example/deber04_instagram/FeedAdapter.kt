package com.example.deber04_instagram

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.location.GnssAntennaInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.feed_item.view.*

class FeedAdapter(private val posts :ArrayList<FeedPost>, private val listener: OnAdapterListener)
    :RecyclerView.Adapter<FeedAdapter.ViewHolder>() {



    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.feed_item,parent,false)
        )
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]

        holder.view.username_text.text = post.username
        holder.view.user_photo_image.setImageResource(post.image)
        holder.view.caption_text.text = post.caption
        holder.view.likes_text.text = post.likes
        holder.view.post_image.setImageResource(post.photo)

        holder.view.like_image.setOnClickListener{
            listener.onSimpleClick(post)
        }

        }


    fun setData(newList: List<FeedPost>) {
        posts.clear()
        posts.addAll(newList)
    }


    interface OnAdapterListener{
        fun onSimpleClick(posts: FeedPost)
    }
}