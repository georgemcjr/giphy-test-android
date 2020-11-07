package br.com.gj.giphytest.features.favorites

import androidx.lifecycle.LiveData
import br.com.gj.giphytest.model.GifItem
import io.reactivex.Completable

class FavoritesLocalDataSource(private val favoritesDao: FavoritesDao) {

    fun addFavorite(gif: GifItem): Completable {
        return favoritesDao.insertItem(gif)
    }

    fun removeFavorite(gif: GifItem): Completable {
        return favoritesDao.remoteItem(gif)
    }

    fun getAllFavorites(): LiveData<List<GifItem>> {
        return favoritesDao.getAll()
    }
}
