package br.com.gj.giphytest.features.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.gj.giphytest.features.common.model.GifItem
import br.com.gj.giphytest.features.common.usecases.RemoveFavoriteGifUseCase
import br.com.gj.giphytest.features.favorites.usecases.GetAllFavoritesGifUseCase

class FavoritesViewModel(
    getAllFavoritesGifUseCase: GetAllFavoritesGifUseCase,
    private val removeFavoriteGifUseCase: RemoveFavoriteGifUseCase
) : ViewModel() {

    val favoritesLiveData: LiveData<List<GifItem>> = getAllFavoritesGifUseCase.getAllFavorites()

    fun removeFavorite(item: GifItem) {
        removeFavoriteGifUseCase.removeFavorite(item)
    }

}