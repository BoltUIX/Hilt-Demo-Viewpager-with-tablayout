package com.zig.gps.model

import android.os.Parcelable
import com.zig.gps.model.Article
import kotlinx.parcelize.Parcelize


@Parcelize
data class NewResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
):Parcelable