package br.com.gj.giphytest.features.common.usecases

import br.com.gj.giphytest.features.common.model.GifItem
import br.com.gj.giphytest.features.favorites.FavoritesLocalDataSource
import br.com.gj.giphytest.util.useDefaultSchedulers

class RemoveFavoriteGifUseCase(private val localDataSource: FavoritesLocalDataSource) : BaseUseCase() {

    fun removeFavorite(gif: GifItem) {
        localDataSource.removeFavorite(gif)
            .useDefaultSchedulers()
            .subscribe()
            .collect()
    }
}