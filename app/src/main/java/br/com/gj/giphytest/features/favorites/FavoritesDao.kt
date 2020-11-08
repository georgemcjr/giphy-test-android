package br.com.gj.giphytest.features.favorites

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.gj.giphytest.model.GifItem
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(vararg gif: GifItem) : Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(gif: List<GifItem>) : Completable

    @Delete
    fun remoteItem(vararg gif: GifItem) : Completable

    @Query("SELECT * FROM favorites")
    fun getAll() : LiveData<List<GifItem>>

    @Query("SELECT * FROM favorites")
    fun getAllRx() : Observable<List<GifItem>>
}