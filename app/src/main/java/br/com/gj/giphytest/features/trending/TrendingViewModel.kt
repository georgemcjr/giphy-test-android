package br.com.gj.giphytest.features.trending

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.gj.giphytest.model.State
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TrendingViewModel(
    private val trendingRepository: TrendingRepository
) : ViewModel() {

    private val tag = TrendingViewModel::class.java.canonicalName

    private val compositeDisposable = CompositeDisposable()

    private val _gifListLiveData = MutableLiveData<State>()
    val gifListLiveData : LiveData<State>
        get() = _gifListLiveData

    fun fetchTrendingGifs() {
        // TODO put this fetch business logic in the Repository (or UseCase)
        //  see: https://developer.android.com/jetpack/guide#fetch-data
        val disposable = trendingRepository.fetchTrendingGifList()
            .subscribeOn(Schedulers.io()) // TODO: add schedulers via DI
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _gifListLiveData.value = State.Loading
            }
            .subscribe { response, error ->
                Log.d(tag, "Response: $response")
                Log.d(tag, "Error: $error")
                if (response != null) {
                    _gifListLiveData.value = State.Success(response)
                } else if (error != null) {
                    _gifListLiveData.value = State.Error(error)
                }
            }

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}
