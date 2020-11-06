package br.com.gj.giphytest.features.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import br.com.gj.giphytest.features.favorites.AddFavoriteGifUseCase
import br.com.gj.giphytest.features.favorites.GetAllFavoritesGifUseCase
import br.com.gj.giphytest.features.favorites.RemoveFavoriteGifUseCase
import br.com.gj.giphytest.model.GifItem
import br.com.gj.giphytest.model.State

class TrendingViewModel(
    private val getTrendingGifListUseCase: GetTrendingGifsUseCase,
    private val addFavoriteGifUseCase: AddFavoriteGifUseCase,
    private val removeFavoriteGifUseCase: RemoveFavoriteGifUseCase,
    getAllFavoritesGifUseCase: GetAllFavoritesGifUseCase
) : ViewModel() {

    val gifListLiveData: LiveData<State> = getTrendingGifListUseCase.fetchTrendingGifs()

    val favoriteListLiveData: LiveData<List<GifItem>> = getAllFavoritesGifUseCase.getAllFavorites()

    // TODO Refactor this code
    // region need refactoring
    val liveDataMerger = MediatorLiveData<State>().apply {
        var fromNetwork: State? = null
        var fromDatabase: List<GifItem>? = null

        fun update() {
            fromNetwork.let { state ->
                if (state is State.Success<*>) {
                    val completeList : List<GifItem> = state.safeContent()
                    val mergedList = markFavorites(completeList, fromDatabase ?: emptyList())
                    this.value = State.Success(mergedList)
                } else {
                    this.value = fromNetwork
                }
            }
        }

        addSource(gifListLiveData) { state ->
            fromNetwork = state
            update()
        }

        addSource(favoriteListLiveData) { items ->
            fromDatabase = items
            update()
        }
    }

    private fun markFavorites(completeList: List<GifItem>, favoriteList: List<GifItem>): List<GifItem> {
        val result = completeList.toMutableList()

        favoriteList.forEach { item ->
            val index = result.indexOfFirst {
                item.id == it.id
            }

            result[index] = item
        }

        return result
    }
    // endregion need refactoring

    fun addFavorite(item: GifItem) {
        addFavoriteGifUseCase.addFavorite(item)
    }

    fun removeFavorite(item: GifItem) {
        removeFavoriteGifUseCase.removeFavorite(item)
    }

    override fun onCleared() {
        getTrendingGifListUseCase.clearDisposables()
        super.onCleared()
    }
}
