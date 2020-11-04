package br.com.gj.giphytest.features.favorites

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.gj.giphytest.data.DatabaseManager
import br.com.gj.giphytest.model.GifItem
import org.hamcrest.Matchers
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class FavoritesDaoTest {

    // TODO fix unit tests
    // TODO create Trampoline scheduler

    private lateinit var database: DatabaseManager.AppDatabase
    private lateinit var favoritesDao: FavoritesDao

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            DatabaseManager.AppDatabase::class.java
        ).build()
        favoritesDao = database.favoritesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insertOneFavorite() {
        val gif = createGif()

        val rowId = favoritesDao.insertItem(gif)

        assertThat(rowId[0], notNullValue())
    }

    @Test
    fun insertOneFavoriteAndReadIt() {
        val gif = createGif()

        favoritesDao.insertItem(gif)

        val result = favoritesDao.getAll()

        assertThat(result, Matchers.hasSize(1))
        assertThat(result[0], `is`(gif))
    }

    @Test
    fun insertTwoFavoritesAndReadThem() {
        val gif1 = createGif("1")
        val gif2 = createGif("2")

        favoritesDao.insertItem(gif1, gif2)

        val result = favoritesDao.getAll()

        assertThat(result, Matchers.hasSize(2))
        assertThat(result[0], `is`(gif1))
        assertThat(result[1], `is`(gif2))
    }

    @Test
    fun removeFavorite() {
        val gif1 = createGif("1")
        val gif2 = createGif("2")

        favoritesDao.insertItem(gif1, gif2)

        favoritesDao.remoteItem(gif1)

        val result = favoritesDao.getAll()

        assertThat(result, Matchers.hasSize(1))
        assertThat(result[0], `is`(gif2))
    }

    private fun createGif(id: String = "1"): GifItem {
        return GifItem(
            id = id,
            gifUrl = "url"
        )
    }
}