package br.com.gj.giphytest.features.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.gj.giphytest.model.State
import br.com.gj.giphytest.model.TrendingItem

class TrendingViewModel(
    private val getTrendingGifsUseCase: GetTrendingGifsUseCase
) : ViewModel() {

    val gifListLiveData : LiveData<State> = getTrendingGifsUseCase.fetchTrendingGifs()

    override fun onCleared() {
        getTrendingGifsUseCase.clearDisposables()
        super.onCleared()
    }

    fun addFavorite(item: TrendingItem) {
//        trendingRepository.addFavorite
    }
}
