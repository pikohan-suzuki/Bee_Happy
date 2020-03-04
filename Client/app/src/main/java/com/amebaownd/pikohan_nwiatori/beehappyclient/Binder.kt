package com.amebaownd.pikohan_nwiatori.beehappyclient

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:resource")
fun setResourceToImageView(imageView: ImageView, resource: Int?){
    if(resource!=null){
        imageView.setImageResource(resource)
    }
}