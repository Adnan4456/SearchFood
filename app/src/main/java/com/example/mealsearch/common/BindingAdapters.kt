package com.example.mealsearch.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mealsearch.R

@BindingAdapter ("urlToImage")
fun urlToImage(view:ImageView , str: String?){

    str?.let {

        Glide.with(view)
            .load(str)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.error)
            .transition(DrawableTransitionOptions.withCrossFade(200))
            .into(view)
    }
}

