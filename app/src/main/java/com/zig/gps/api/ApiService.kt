package com.zig.gps.api

import com.zig.gps.model.GetDeviceData
import com.zig.gps.model.LoginData
import com.zig.gps.model.Logout
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("api/getDevice/{client_id}")
    suspend fun getDeviceDetails(@Path(value = "client_id", encoded = true) client_id :Int ): Response<List<GetDeviceData>>

    @POST("login")
    suspend fun getLoginVerify(@Body requestBody: RequestBody): Response<LoginData>

    @POST("logout")
    suspend fun getLogout(@Header("Authorization") auth: String): Response<Logout>
}


