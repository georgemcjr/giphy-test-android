package br.com.gj.giphytest.features.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.gj.giphytest.model.GifItem

class FavoritesViewModel(
    getAllFavoritesGifUseCase: GetAllFavoritesGifUseCase,
    removeFavoriteGifUseCase: RemoveFavoriteGifUseCase
) : ViewModel() {

    val favoritesLiveData: LiveData<List<GifItem>> = getAllFavoritesGifUseCase.getAllFavorites()

}