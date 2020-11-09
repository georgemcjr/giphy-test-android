package br.com.gj.giphytest.features.common.usecases

import br.com.gj.giphytest.features.common.model.GifItem
import br.com.gj.giphytest.features.favorites.FavoritesLocalDataSource
import br.com.gj.giphytest.util.useDefaultSchedulers

class AddFavoriteGifUseCase(private val localDataSource: FavoritesLocalDataSource) : BaseUseCase() {

    fun addFavorite(gif: GifItem) {
        val gifToSave = gif.copy(isFavorite = true)

        localDataSource.addFavorite(gifToSave)
            .useDefaultSchedulers()
            .subscribe()
            .collect()
    }

}
