package com.meetrickandmorty.app.extensions

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.databinding.BindingAdapter

@BindingAdapter("isVisible")
fun View.setVisibility(isVisible: Boolean) {
    visibility = if (isVisible) VISIBLE else GONE
}