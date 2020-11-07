package br.com.gj.giphytest.features.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.gj.giphytest.domain.BaseUseCase
import br.com.gj.giphytest.features.trending.RemoteDataSource
import br.com.gj.giphytest.model.GifItemMapper
import br.com.gj.giphytest.model.State

class SearchGifsUseCase(
    private val remoteDataSource: RemoteDataSource
) : BaseUseCase() {

    fun searchGifs(query: String, gifListLiveData: MutableLiveData<State>): LiveData<State> =
        fetchRemoteDataReceivingStateChanges(
            remoteDataSource.searchGifList(query),
            GifItemMapper::mapFromResponse,
            gifListLiveData
        )

}