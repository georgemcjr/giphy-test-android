package br.com.gj.giphytest.features.favorites

import androidx.lifecycle.LiveData
import br.com.gj.giphytest.model.GifItem

class GetAllFavoritesGifUseCase(private val localDataSource: FavoritesLocalDataSource) {

    fun getAllFavorites(): LiveData<List<GifItem>> {
        return localDataSource.getAllFavorites()
    }

}
