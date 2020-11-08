package br.com.gj.giphytest.features.favorites

import br.com.gj.giphytest.domain.BaseUseCase
import br.com.gj.giphytest.model.GifItem
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