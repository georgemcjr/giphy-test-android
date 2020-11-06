package br.com.gj.giphytest.features.favorites

import br.com.gj.giphytest.domain.BaseUseCase
import br.com.gj.giphytest.model.GifItem
import br.com.gj.giphytest.util.useDefaultSchedulers

class RemoveFavoriteGifUseCase(private val localDataSource: FavoritesLocalDataSource) : BaseUseCase() {

    fun removeFavorite(gif: GifItem) {
        localDataSource.removeFavorite(gif)
            .useDefaultSchedulers()
            .subscribe()
            .collect()
    }
}