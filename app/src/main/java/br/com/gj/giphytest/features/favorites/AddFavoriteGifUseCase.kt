package br.com.gj.giphytest.features.favorites

import br.com.gj.giphytest.domain.BaseUseCase
import br.com.gj.giphytest.model.GifItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AddFavoriteGifUseCase(private val localDataSource: FavoritesLocalDataSource) : BaseUseCase() {

    fun addFavorite(gif: GifItem) {
        val gifToSave = gif.copy(isFavorite = true)

        localDataSource.addFavorite(gifToSave)
            .subscribeOn(Schedulers.io()) // TODO: add schedulers via DI
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .collect()
    }

}
