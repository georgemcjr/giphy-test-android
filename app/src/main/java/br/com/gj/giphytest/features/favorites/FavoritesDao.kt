package br.com.gj.giphytest.features.favorites

import androidx.room.*
import br.com.gj.giphytest.model.TrendingItem

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(vararg gif: TrendingItem) : Array<Long>

    @Query("SELECT * FROM favorites")
    fun getAll() : List<TrendingItem>

    @Delete
    fun remoteItem(vararg gif: TrendingItem) : Int
}