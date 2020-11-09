package br.com.gj.giphytest.features.favorites.usecases

import androidx.lifecycle.LiveData
import br.com.gj.giphytest.features.common.model.GifItem
import br.com.gj.giphytest.features.favorites.FavoritesLocalDataSource
import io.reactivex.Observable

class GetAllFavoritesGifUseCase(private val localDataSource: FavoritesLocalDataSource) {

    fun getAllFavorites(): LiveData<List<GifItem>> {
        return localDataSource.getAllFavorites()
    }

    fun getAllFavoritesRx(): Observable<List<GifItem>> {
        return localDataSource.getAllFavoritesRx()
    }

}
