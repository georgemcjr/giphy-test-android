package br.com.gj.giphytest.features.trending

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TrendingViewModel(
    private val trendingRepository: TrendingRepository
) : ViewModel() {

    private val tag = TrendingViewModel::class.java.canonicalName

    private val compositeDisposable = CompositeDisposable()

    fun fetchTrendingGifs() {
        val disposable = trendingRepository.fetchTrendingGifList()
            .subscribeOn(Schedulers.io()) // TODO: add schedulers via DI
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response, error ->
                Log.d(tag, "Response: $response")
                Log.d(tag, "Error: $error")
            }

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}

sealed class State {
    class Success : State()
    class Error : State()
    class Loading : State()
}


