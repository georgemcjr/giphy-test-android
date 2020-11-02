package br.com.gj.giphytest.features.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TrendingViewModel(
    private val trendingRepository: TrendingRepository
) : ViewModel() {

    private val _gifListLiveData = MutableLiveData<State>()
    val gifListLiveData: LiveData<State>
        get() = _gifListLiveData

    fun fetchTrendingGifs() {
        trendingRepository.fetchTrendingGifsUseCase()
    }
}

sealed class State {
    class Success : State()
    class Error : State()
    class Loading : State()
}


