package com.zig.gps.model

import com.google.gson.annotations.SerializedName


data class LoginData (

    @SerializedName("access_token"  ) var accessToken  : String? = null,
    @SerializedName("refresh_token" ) var refreshToken : String? = null

)