package br.com.gj.giphytest.features.favorites

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.gj.giphytest.model.GifItem
import io.reactivex.Completable

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(vararg gif: GifItem) : Completable

    @Delete
    fun remoteItem(vararg gif: GifItem) : Completable

    @Query("SELECT * FROM favorites")
    fun getAll() : LiveData<List<GifItem>>
}