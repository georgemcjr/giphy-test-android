package br.com.gj.giphytest.features.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.gj.giphytest.domain.BaseUseCase
import br.com.gj.giphytest.features.favorites.CombineWithFavoriteGifsUseCase
import br.com.gj.giphytest.model.GifItemMapper
import br.com.gj.giphytest.model.State

class GetTrendingGifsUseCase(
    private val remoteDataSource: RemoteDataSource,
    private val combineWithFavoriteGifsUseCase: CombineWithFavoriteGifsUseCase
) : BaseUseCase() {

    fun fetchTrendingGifs(stateListenerLiveData: MutableLiveData<State>): LiveData<State> =
        fetchDataDataReceivingStateChanges(
            combineWithFavoriteGifsUseCase.combineWith(fetchTrendingAndMapGifList()),
            stateListenerLiveData
        )

    private fun fetchTrendingAndMapGifList() =
        remoteDataSource.fetchTrendingGifList()
            .map(GifItemMapper::mapFromResponse)
            .toObservable()

    override fun clearDisposables() {
        combineWithFavoriteGifsUseCase.clearDisposables()
        super.clearDisposables()
    }

}
