package br.com.gj.giphytest.features.favorites

import br.com.gj.giphytest.domain.BaseUseCase
import br.com.gj.giphytest.model.GifItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RemoveFavoriteGifUseCase(private val localDataSource: FavoritesLocalDataSource) : BaseUseCase() {

    fun removeFavorite(gif: GifItem) {
        localDataSource.removeFavorite(gif)
            .subscribeOn(Schedulers.io()) // TODO: add schedulers via DI
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .collect()
    }
}