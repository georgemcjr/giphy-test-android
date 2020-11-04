package br.com.gj.giphytest.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.gj.giphytest.features.favorites.FavoritesDao
import br.com.gj.giphytest.model.TrendingItem

object DatabaseManager {

    @Volatile
    private lateinit var INSTANCE: AppDatabase

    fun init(context: Context) {
         synchronized(this) {
            INSTANCE = Room.databaseBuilder(
                context,
                AppDatabase::class.java, "gifs.db"
            ).build()
        }
    }

    fun getInstance() : AppDatabase = INSTANCE

    @Database(entities = [TrendingItem::class], version = 1, exportSchema = false)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun favoritesDao() : FavoritesDao
    }
}