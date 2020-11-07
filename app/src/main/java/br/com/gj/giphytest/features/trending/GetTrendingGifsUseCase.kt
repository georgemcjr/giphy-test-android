package br.com.gj.giphytest.features.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.gj.giphytest.domain.BaseUseCase
import br.com.gj.giphytest.model.GifItemMapper
import br.com.gj.giphytest.model.State
import br.com.gj.giphytest.util.useDefaultSchedulers

class GetTrendingGifsUseCase(
    private val remoteDataSource: TrendingRemoteDataSource
) : BaseUseCase() {

    fun fetchTrendingGifs(gifListLiveData: MutableLiveData<State>) : LiveData<State> {

        remoteDataSource.fetchTrendingGifList()
            .useDefaultSchedulers()
            .doOnSubscribe {
                gifListLiveData.value = State.Loading
            }
            .map { response ->
                GifItemMapper.mapFromTrendingResponse(response)
            }
            .doOnSuccess { gitItemList ->
                gifListLiveData.value = State.Success(gitItemList)
            }
            .doOnError { error ->
                gifListLiveData.value = State.Error(error)
            }
            .subscribe()
            .collect()

        return gifListLiveData
    }
}
