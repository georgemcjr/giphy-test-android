package br.com.gj.giphytest.features.favorites.usecases

import br.com.gj.giphytest.features.common.model.GifItem
import br.com.gj.giphytest.features.common.usecases.BaseUseCase
import io.reactivex.Observable

class CombineWithFavoriteGifsUseCase(
    private val getAllFavoritesGifUseCase: GetAllFavoritesGifUseCase
) : BaseUseCase() {

    fun combineWith(fetchOperation: Observable<List<GifItem>>): Observable<List<GifItem>> {
        return Observable.combineLatest(
            fetchOperation,
            getAllFavoritesGifUseCase.getAllFavoritesRx(),
            { fetched, favs ->
                mergeWithFavorites(fetched, favs)
            }
        )
    }

    private fun mergeWithFavorites(
        allGifList: List<GifItem>,
        favoriteGifList: List<GifItem>
    ): List<GifItem> {
        val result = allGifList.toMutableList()

        favoriteGifList.forEach { item ->
            val index = result.indexOfFirst {
                item.id == it.id
            }

            if (index != -1) {
                result[index] = item
            }
        }

        return result
    }

}