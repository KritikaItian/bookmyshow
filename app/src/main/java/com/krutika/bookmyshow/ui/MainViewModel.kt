package com.krutika.bookmyshow.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.krutika.bookmyshow.data.remote.models.VenuesItem
import com.krutika.bookmyshow.data.repository.showTimeRepository
import com.krutika.bookmyshow.ui.common.BaseViewModel
import com.krutika.bookmyshow.utils.ext.toLoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val showTimeRepository: showTimeRepository,
) : BaseViewModel() {

    var showTimeResponse = MutableLiveData<ArrayList<VenuesItem>>()


    init {
        showTimeResponse = MutableLiveData()
    }

    fun getShowtimeList(): MutableLiveData<ArrayList<VenuesItem>> {
        viewModelScope.launch {
            showTimeRepository
                .getShowTimeList()
                .flowOn(Dispatchers.IO)
                .toLoadingState()
                .collectLatest { result ->
                    result.handleErrorAndLoadingState()
                    result.getValueOrNull()?.let { showtime ->
                        showTimeResponse.value = showtime.venues!!
                    }
                }
        }
        return showTimeResponse
    }

}