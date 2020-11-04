package br.com.gj.giphytest.features.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.gj.giphytest.features.favorites.AddFavoriteGifUseCase
import br.com.gj.giphytest.features.favorites.GetAllFavoritesGifUseCase
import br.com.gj.giphytest.features.favorites.RemoveFavoriteGifUseCase
import br.com.gj.giphytest.model.GifItem
import br.com.gj.giphytest.model.State

class TrendingViewModel(
    private val getTrendingGifsUseCase: GetTrendingGifsUseCase,
    private val addFavoriteGifUseCase: AddFavoriteGifUseCase,
    private val removeFavoriteGifUseCase: RemoveFavoriteGifUseCase,
    private val getAllFavoritesGifUseCase: GetAllFavoritesGifUseCase
) : ViewModel() {

    val gifListLiveData: LiveData<State> = getTrendingGifsUseCase.fetchTrendingGifs()

    fun addFavorite(item: GifItem) {
        addFavoriteGifUseCase.addFavorite(item)
    }

    fun removeFavorite(item: GifItem) {
        removeFavoriteGifUseCase.removeFavorite(item)
    }

    fun getAll(): LiveData<List<GifItem>> {
        return getAllFavoritesGifUseCase.getAllFavorites()
    }

    override fun onCleared() {
        getTrendingGifsUseCase.clearDisposables()
        super.onCleared()
    }
}
