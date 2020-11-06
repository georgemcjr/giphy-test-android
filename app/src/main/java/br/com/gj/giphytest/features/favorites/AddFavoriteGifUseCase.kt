package br.com.gj.giphytest.features.favorites

import br.com.gj.giphytest.domain.BaseUseCase
import br.com.gj.giphytest.model.GifItem
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
