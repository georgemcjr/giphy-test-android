package br.com.gj.giphytest.features.favorites

import androidx.lifecycle.LiveData
import br.com.gj.giphytest.data.DatabaseManager
import br.com.gj.giphytest.model.GifItem
import io.reactivex.Completable

class FavoritesLocalDataSource {

    fun addFavorite(gif: GifItem): Completable {
        // TODO this Dao could be injected
        return DatabaseManager.getInstance().favoritesDao().insertItem(gif)
    }

    fun removeFavorite(gif: GifItem): Completable {
        // TODO this Dao could be injected
        return DatabaseManager.getInstance().favoritesDao().remoteItem(gif)
    }

    fun getAllFavorites(): LiveData<List<GifItem>> {
        // TODO this Dao could be injected
        return DatabaseManager.getInstance().favoritesDao().getAll()
    }
}
