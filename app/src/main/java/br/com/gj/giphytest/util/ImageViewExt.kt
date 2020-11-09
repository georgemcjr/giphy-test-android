package br.com.gj.giphytest.util

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadFromUrl(url: String) {
    Glide.with(this).load(url).centerCrop().into(this)
}