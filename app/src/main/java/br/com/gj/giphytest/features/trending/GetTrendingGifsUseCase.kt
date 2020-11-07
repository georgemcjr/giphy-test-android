package br.com.gj.giphytest.features.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.gj.giphytest.domain.BaseUseCase
import br.com.gj.giphytest.model.GifItemMapper
import br.com.gj.giphytest.model.State

class GetTrendingGifsUseCase(
    private val remoteDataSource: RemoteDataSource
) : BaseUseCase() {

    fun fetchTrendingGifs(gifListLiveData: MutableLiveData<State>): LiveData<State> =
        fetchRemoteDataReceivingStateChanges(
            remoteDataSource.fetchTrendingGifList(),
            GifItemMapper::mapFromResponse,
            gifListLiveData
        )
}
