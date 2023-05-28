package com.krutika.bookmyshow.ui.showlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
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
class ShowlistViewModel @Inject constructor(
    private val showTimeRepository: showTimeRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    var showTimeResponse = MutableLiveData<ArrayList<VenuesItem>>()

    val _venue =
        savedStateHandle.getLiveData<VenuesItem?>("venue", null)

    val venue = _venue

    val getTimeSlot = venue.value?.showtimes


    init {
        showTimeResponse = MutableLiveData()
    }

}