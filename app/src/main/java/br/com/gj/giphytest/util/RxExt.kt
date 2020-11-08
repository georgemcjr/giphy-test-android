package br.com.gj.giphytest.util

import br.com.gj.giphytest.di.InjectionManager
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import org.koin.java.KoinJavaComponent.get

fun Completable.useDefaultSchedulers(): Completable =
    subscribeOn(get(Scheduler::class.java, InjectionManager.ioScheduler))
        .observeOn(get(Scheduler::class.java, InjectionManager.mainThreadScheduler))

fun <T> Single<T>.useDefaultSchedulers(): Single<T> =
    subscribeOn(get(Scheduler::class.java, InjectionManager.ioScheduler))
        .observeOn(get(Scheduler::class.java, InjectionManager.mainThreadScheduler))

fun <T> Observable<T>.useDefaultSchedulers(): Observable<T> =
    subscribeOn(get(Scheduler::class.java, InjectionManager.ioScheduler))
        .observeOn(get(Scheduler::class.java, InjectionManager.mainThreadScheduler))
