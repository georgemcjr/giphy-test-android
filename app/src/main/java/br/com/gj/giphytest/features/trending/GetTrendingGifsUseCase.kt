package br.com.gj.giphytest.features.trending

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.gj.giphytest.domain.BaseUseCase
import br.com.gj.giphytest.model.State
import br.com.gj.giphytest.model.TrendingItemMapper
import br.com.gj.giphytest.util.useDefaultSchedulers

class GetTrendingGifsUseCase(
    private val remoteDataSource: TrendingRemoteDataSource
) : BaseUseCase() {

    fun fetchTrendingGifs() : LiveData<State> {
        val gifListLiveData = MutableLiveData<State>()

        remoteDataSource.fetchTrendingGifList()
            .useDefaultSchedulers()
            .doOnSubscribe {
                gifListLiveData.value = State.Loading
            }
            .map {
                TrendingItemMapper.mapFromResponse(it)
            }
            .subscribe { response, error ->
                Log.d("tag", "Response: $response")
                Log.d("tag", "Error: $error")
                if (response != null) {
                    gifListLiveData.value = State.Success(response)
                } else if (error != null) {
                    gifListLiveData.value = State.Error(error)
                }
            }.collect()

        return gifListLiveData
    }
}
