package br.com.gj.giphytest.features.trending

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.gj.giphytest.domain.BaseUseCase
import br.com.gj.giphytest.model.State
import br.com.gj.giphytest.model.TrendingItemMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetTrendingGifsUseCase(
    private val remoteDataSource: TrendingRemoteDataSource
) : BaseUseCase() {

    fun fetchTrendingGifs() : LiveData<State> {
        val gifListLiveData = MutableLiveData<State>()

        remoteDataSource.fetchTrendingGifList()
            .subscribeOn(Schedulers.io()) // TODO: add schedulers via DI
            .observeOn(AndroidSchedulers.mainThread())
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
