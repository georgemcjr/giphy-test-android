package br.com.gj.giphytest.features.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.gj.giphytest.features.common.model.GifItem
import br.com.gj.giphytest.features.common.model.State
import br.com.gj.giphytest.features.common.usecases.AddFavoriteGifUseCase
import br.com.gj.giphytest.features.common.usecases.RemoveFavoriteGifUseCase
import br.com.gj.giphytest.features.trending.usecases.GetTrendingGifsUseCase
import br.com.gj.giphytest.features.trending.usecases.SearchGifsUseCase

class TrendingViewModel(
    private val getTrendingGifListUseCase: GetTrendingGifsUseCase,
    private val searchGifsUseCase: SearchGifsUseCase,
    private val addFavoriteGifUseCase: AddFavoriteGifUseCase,
    private val removeFavoriteGifUseCase: RemoveFavoriteGifUseCase,
) : ViewModel() {

    private val _gifListLiveData: MutableLiveData<State> = MutableLiveData<State>()

    val gifListLiveData: LiveData<State>
        get() = _gifListLiveData

    fun addFavorite(item: GifItem) {
        addFavoriteGifUseCase.addFavorite(item)
    }

    fun removeFavorite(item: GifItem) {
        removeFavoriteGifUseCase.removeFavorite(item)
    }

    fun fetchGifs(query: String = "") : LiveData<State> {

        // Need to clear disposables before fetching data
        // because the last Observable is still active and
        // will emit elements if any item is added/remove to/from
        // favorites (i.e. changes in favorites table in the database)
        // This is due to the Observable.combineLatest operator used in
        // the CombineWithFavoriteGifsUseCase
        //
        // Using the database as the Single Source of Truth and caching
        // the responses on the database would be a better approach to share
        // the state of the gifs between the TRENDING and FAVORITES fragments
        getTrendingGifListUseCase.clearDisposables()
        searchGifsUseCase.clearDisposables()

        return if (query.isBlank()) {
            getTrendingGifListUseCase.fetchTrendingGifs(_gifListLiveData)
        } else {
            searchGifsUseCase.searchGifs(query, _gifListLiveData)
        }
    }

    override fun onCleared() {
        searchGifsUseCase.clearDisposables()
        getTrendingGifListUseCase.clearDisposables()
        addFavoriteGifUseCase.clearDisposables()
        removeFavoriteGifUseCase.clearDisposables()
        super.onCleared()
    }
}
