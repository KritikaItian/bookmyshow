package com.krutika.bookmyshow.ui.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.krutika.bookmyshow.utils.retrofit.LoadState
import com.krutika.bookmyshow.utils.ext.AppError
import com.krutika.bookmyshow.utils.ext.toAppError
import com.krutika.bookmyshow.utils.retrofit.SingleLiveDataEvent

abstract class BaseViewModel : ViewModel() {
    var errorLiveData: MutableLiveData<SingleLiveDataEvent<AppError>> = MutableLiveData()
    fun onError(error: AppError) {
        errorLiveData.postValue(SingleLiveDataEvent(error))
    }

    var messageLiveData: MutableLiveData<SingleLiveDataEvent<String>> = MutableLiveData()
    fun showMessage(message: String) {
        messageLiveData.postValue(SingleLiveDataEvent(message))
    }

    var loadingLiveData: MutableLiveData<SingleLiveDataEvent<Boolean>> = MutableLiveData()
    fun showLoading(visible: Boolean) {
        loadingLiveData.postValue(SingleLiveDataEvent(visible))
    }

    fun LoadState<Any?>.handleErrorAndLoadingState() {
        showLoading(isLoading)
        getErrorIfExists().toAppError()?.let { appError -> onError(appError) }
    }

    override fun onCleared() {
        super.onCleared()
        showLoading(false)
    }
}
