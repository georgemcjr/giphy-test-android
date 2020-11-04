package br.com.gj.giphytest.domain

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

}