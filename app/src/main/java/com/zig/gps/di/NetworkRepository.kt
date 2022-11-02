package com.zig.gps.di

import com.zig.gps.api.ApiService
import com.zig.gps.model.GetDeviceData
import com.zig.gps.model.LoginData
import com.zig.gps.model.Logout
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getDeviceDetails(client_id: Int): Response<List<GetDeviceData>> {
        return apiService.getDeviceDetails(client_id)
    }

    suspend fun getLogin(request: RequestBody): Response<LoginData> {
        return apiService.getLoginVerify(request)
    }

    suspend fun getLogout(auth: String): Response<Logout> {
        return apiService.getLogout(auth)
    }
}