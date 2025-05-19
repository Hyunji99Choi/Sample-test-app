package com.example.sampleapp.presentation

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.sampleapp.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@BindingAdapter("thumbnail_url")
fun setThumbnail(view: ImageView, url: String?) {
    url?.let {
        Glide.with(view.context)
            .load(it)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_dashboard_black_24dp)
            /*.apply(
                RequestOptions()
                    .transform(
                        CenterCrop(),
                        RoundedCorners(
                            view.resources.getDimensionPixelOffset(R.dimen.thumbnail_radius),
                        )
                    )
            )*/
            .into(view)
    }
}

@BindingAdapter("date_ui_format")
fun setDate(view: TextView, date: Date?) {
    if (date!= null) {
        view.text = SimpleDateFormat("yyyy.MM.dd\nHH:mm", Locale.getDefault()).format(date)
    }
}