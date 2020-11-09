package br.com.gj.giphytest.features.favorites

import androidx.lifecycle.LiveData
import br.com.gj.giphytest.data.FavoritesDao
import br.com.gj.giphytest.features.common.model.GifItem
import io.reactivex.Completable
import io.reactivex.Observable

class FavoritesLocalDataSource(private val favoritesDao: FavoritesDao) {

    fun addFavorite(gif: GifItem): Completable {
        return favoritesDao.insertItem(gif)
    }

    fun addFavorite(gifList: List<GifItem>): Completable {
        return favoritesDao.insertItem(gifList)
    }

    fun removeFavorite(gif: GifItem): Completable {
        return favoritesDao.remoteItem(gif)
    }

    fun getAllFavorites(): LiveData<List<GifItem>> {
        return favoritesDao.getAll()
    }

    fun getAllFavoritesRx(): Observable<List<GifItem>> {
        return favoritesDao.getAllRx()
    }
}
