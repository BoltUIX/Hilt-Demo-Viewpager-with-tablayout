package com.zig.gps.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class GetDeviceData (

    @SerializedName("OdometerValue") var OdometerValue : String,
    @SerializedName("added_date") var addedDate : String,
    @SerializedName("client_id") var clientId : Int,
    @SerializedName("id") var id : Int,
    @SerializedName("imei") var imei : String,
    @SerializedName("simno") var simno : String,
    @SerializedName("status") var status : Boolean,
    @SerializedName("vehicleNo") var vehicleNo : String

)