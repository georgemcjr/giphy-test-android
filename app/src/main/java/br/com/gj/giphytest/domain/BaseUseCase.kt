package br.com.gj.giphytest.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.gj.giphytest.model.State
import br.com.gj.giphytest.util.useDefaultSchedulers
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseUseCase {

    private val compositeDisposable = CompositeDisposable()

    open fun clearDisposables() {
        compositeDisposable.clear()
    }

    fun Disposable.collect() {
        compositeDisposable.add(this)
    }

    fun <Response> fetchDataDataReceivingStateChanges(
        responseSingle: Observable<Response>,
        stateListenerLiveData: MutableLiveData<State>
    ): LiveData<State> {

        responseSingle
            .useDefaultSchedulers()
            .doOnSubscribe {
                stateListenerLiveData.value = State.Loading
            }
            .doOnNext { model ->
                stateListenerLiveData.value = State.Success(model)
            }
            .doOnError { error ->
                stateListenerLiveData.value = State.Error(error)
            }
            .subscribe()
            .collect()

        return stateListenerLiveData
    }

}