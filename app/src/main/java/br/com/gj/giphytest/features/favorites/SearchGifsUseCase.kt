package br.com.gj.giphytest.features.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.gj.giphytest.domain.BaseUseCase
import br.com.gj.giphytest.features.trending.RemoteDataSource
import br.com.gj.giphytest.model.GifItemMapper
import br.com.gj.giphytest.model.State

class SearchGifsUseCase(
    private val remoteDataSource: RemoteDataSource,
    private val combineWithFavoriteGifsUseCase: CombineWithFavoriteGifsUseCase
) : BaseUseCase() {

    fun searchGifs(
        query: String,
        stateListenerLiveData: MutableLiveData<State>
    ): LiveData<State> =
        fetchDataDataReceivingStateChanges(
            combineWithFavoriteGifsUseCase.combineWith(searchAndMapGifList(query)),
            stateListenerLiveData
        )

    private fun searchAndMapGifList(query: String) =
        remoteDataSource.searchGifList(query)
            .map(GifItemMapper::mapFromResponse)
            .toObservable()

    override fun clearDisposables() {
        combineWithFavoriteGifsUseCase.clearDisposables()
        super.clearDisposables()
    }

}