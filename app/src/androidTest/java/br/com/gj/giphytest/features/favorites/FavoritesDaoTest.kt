package br.com.gj.giphytest.features.favorites

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.gj.giphytest.RxTestSchedulerRule
import br.com.gj.giphytest.data.DatabaseManager
import br.com.gj.giphytest.model.GifItem
import org.hamcrest.Matchers
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class FavoritesDaoTest {

    @get:Rule
    val rxTestSchedulersRule = RxTestSchedulerRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

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

        val result = favoritesDao.insertItem(gif)
        val testObserver = result.test()
        testObserver.awaitTerminalEvent()

        testObserver.assertSubscribed()
        testObserver.assertComplete()
    }

    @Test
    fun insertOneFavoriteAndReadIt() {
        val gif = createGif()

        favoritesDao.insertItem(gif).test()

        val result = favoritesDao.getAll()

        val observer = Observer<List<GifItem>> {
            assertThat(it, Matchers.hasSize(1))
            assertThat(it[0], `is`(gif))
        }

        fireLiveData(result, observer)
    }

    @Test
    fun insertTwoFavoritesAndReadThem() {
        val gif1 = createGif("1")
        val gif2 = createGif("2")

        favoritesDao.insertItem(gif1, gif2).test()

        val result = favoritesDao.getAll()

        val observer = Observer<List<GifItem>> {
            assertThat(it, Matchers.hasSize(2))
            assertThat(it[0], `is`(gif1))
            assertThat(it[1], `is`(gif2))
        }

        fireLiveData(result, observer)
    }

    @Test
    fun removeFavorite() {
        val gif1 = createGif("1")
        val gif2 = createGif("2")

        favoritesDao.insertItem(gif1, gif2).test()

        favoritesDao.remoteItem(gif1).test()

        val result = favoritesDao.getAll()

        val observer = Observer<List<GifItem>> {
            assertThat(it, Matchers.hasSize(1))
            assertThat(it[0], `is`(gif2))
        }

        fireLiveData(result, observer)
    }

    private fun createGif(id: String = "1"): GifItem {
        return GifItem(
            id = id,
            gifUrl = "url"
        )
    }

    private fun <T> fireLiveData(
        result: LiveData<T>,
        observer: Observer<T>
    ) {
        try {
            result.observeForever(observer)
        } finally {
            result.removeObserver(observer)
        }
    }
}