package com.zig.gps.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zig.gps.di.NetworkRepository
import com.zig.gps.model.GetDeviceData
import com.zig.gps.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
 class DashboardViewModel @Inject constructor(private val networkRepository: NetworkRepository) : ViewModel() {

    private val _getDeviceDetails: MutableStateFlow<DataHandler<List<GetDeviceData>>> = MutableStateFlow(
       DataHandler.EMPTY())

    val getDeviceDetails: StateFlow <DataHandler<List<GetDeviceData>>> = _getDeviceDetails

    fun getDeviceDetails() {
        _getDeviceDetails.value = DataHandler.EMPTY()
        viewModelScope.launch {
            try{
                val response = networkRepository.getDeviceDetails(1)
                _getDeviceDetails.value =handleResponse(response)
            }catch (e:Exception){
                _getDeviceDetails.value = DataHandler.ERROR(null, e.message.toString())
            }
        }
    }

    private fun handleResponse(response: Response<List<GetDeviceData>>): DataHandler<List<GetDeviceData>> {
        if (response.isSuccessful) {
            response.body()?.let { it ->
                return DataHandler.SUCCESS(it)
            }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }
}