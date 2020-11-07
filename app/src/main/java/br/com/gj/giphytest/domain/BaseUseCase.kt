package br.com.gj.giphytest.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.gj.giphytest.model.State
import br.com.gj.giphytest.util.useDefaultSchedulers
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseUseCase {

    private val compositeDisposable = CompositeDisposable()

    fun clearDisposables() {
        compositeDisposable.dispose()
    }

    fun Disposable.collect() {
        compositeDisposable.add(this)
    }

    fun <Response, Model> fetchRemoteDataReceivingStateChanges(
        responseSingle: Single<Response>,
        mapper: (Response) -> (Model),
        stateLiveData: MutableLiveData<State>
    ): LiveData<State> {

        responseSingle
            .useDefaultSchedulers()
            .doOnSubscribe {
                stateLiveData.value = State.Loading
            }
            .map { response ->
                mapper(response)
            }
            .doOnSuccess { gitItemList ->
                stateLiveData.value = State.Success(gitItemList)
            }
            .doOnError { error ->
                stateLiveData.value = State.Error(error)
            }
            .subscribe()
            .collect()

        return stateLiveData
    }

}