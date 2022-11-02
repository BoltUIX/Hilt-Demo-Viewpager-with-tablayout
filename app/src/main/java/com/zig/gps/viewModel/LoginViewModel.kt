package com.zig.gps.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zig.gps.di.NetworkRepository
import com.zig.gps.model.LoginData
import com.zig.gps.model.Logout
import com.zig.gps.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
 class LoginViewModel @Inject constructor(private val networkRepository: NetworkRepository) : ViewModel() {

    // private val _topHeadlines = MutableLiveData<DataHandler<NewResponse>>()
    private val _userLoginResponse: MutableStateFlow<DataHandler<LoginData>> =
        MutableStateFlow(DataHandler.EMPTY())
    val userLoginResponse: StateFlow<DataHandler<LoginData>> = _userLoginResponse


    private val _userLogoutResponse: MutableStateFlow<DataHandler<Logout>> =
        MutableStateFlow(DataHandler.EMPTY())
    val userLogoutResponse: StateFlow<DataHandler<Logout>> = _userLogoutResponse


    private fun handleResponse(response: Response<LoginData>): DataHandler<LoginData> {
        if (response.isSuccessful) {
            response.body()?.let { it ->
                return DataHandler.SUCCESS(it)
            }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

    fun userLoginAuth(formData: RequestBody) = viewModelScope.launch {
        _userLoginResponse.value = DataHandler.EMPTY()
        try {
            val response = networkRepository.getLogin(formData)
            _userLoginResponse.value = handleResponse(response)
        } catch (e: Exception) {
            _userLoginResponse.value = DataHandler.ERROR(null, e.message.toString())
        }

    }

    fun userLogout(auth: String) = viewModelScope.launch {
        _userLogoutResponse.value = DataHandler.EMPTY()
        try {
            val response = networkRepository.getLogout(auth)
            _userLogoutResponse.value = handleResponseLogout(response)
        } catch (e: Exception) {
            _userLogoutResponse.value = DataHandler.ERROR(null, e.message.toString())
        }

    }
    private fun handleResponseLogout(response: Response<Logout>): DataHandler<Logout> {
        if (response.isSuccessful) {
            response.body()?.let { it ->
                return DataHandler.SUCCESS(it)
            }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }
}