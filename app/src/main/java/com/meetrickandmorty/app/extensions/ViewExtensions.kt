package com.meetrickandmorty.app.extensions

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.meetrickandmorty.app.R

@BindingAdapter("isVisible")
fun View.setVisibility(isVisible: Boolean) {
    visibility = if (isVisible) VISIBLE else GONE
}

@BindingAdapter("characterImage")
fun ImageView.loadImage(imageUrl: String) {
    val options = RequestOptions()
        .error(R.drawable.ic_rick_and_morty)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)
        .placeholder(R.drawable.ic_rick_and_morty)
    Glide.with(context)
        .load(imageUrl)
        .apply(options)
        .into(this)
}