package br.com.gj.giphytest.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.gj.giphytest.features.common.model.GifItem

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

    @Database(entities = [GifItem::class], version = 1, exportSchema = false)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun favoritesDao() : FavoritesDao
    }
}