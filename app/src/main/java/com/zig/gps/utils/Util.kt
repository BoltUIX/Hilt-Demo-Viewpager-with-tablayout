package com.zig.gps.utils

import android.util.Log
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.zig.gps.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


fun ImageView.loadImageFromGlide(url: String?) {
    if(url!=null) {
        Glide.with(this)
            .load(url)
            .error(R.drawable.ic_baseline_broken_image_24)
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_hourglass_top_24)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
    }

}

//this.javaClass.simpleName
fun Fragment.LogData(message:String){
    Log.d("test001", "Log -->: "+ message)
}