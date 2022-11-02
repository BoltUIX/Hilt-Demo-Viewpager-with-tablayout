package com.zig.gps.utils.pie

import androidx.annotation.ColorRes
import androidx.annotation.Px

data class Slice(
    val dataPoint: Float,
    @ColorRes val color: Int,
    val name: String,
    var arc: Arc? = null,
    @Px var scaledValue: Float? = 0f,
    var percentage: Int? = null
)

data class Arc(
    val startAngle: Float,
    val sweepAngle: Float
) {
    fun average(): Double =
        (startAngle / 2) + (sweepAngle / 2) + (((startAngle % 2) + (sweepAngle % 2)) / 2).toDouble()
}