package br.com.gj.giphytest.di

import android.app.Application
import br.com.gj.giphytest.data.DatabaseManager
import br.com.gj.giphytest.features.common.usecases.AddFavoriteGifUseCase
import br.com.gj.giphytest.features.common.usecases.RemoveFavoriteGifUseCase
import br.com.gj.giphytest.features.favorites.FavoritesLocalDataSource
import br.com.gj.giphytest.features.favorites.FavoritesViewModel
import br.com.gj.giphytest.features.favorites.usecases.CombineWithFavoriteGifsUseCase
import br.com.gj.giphytest.features.favorites.usecases.GetAllFavoritesGifUseCase
import br.com.gj.giphytest.features.trending.RemoteDataSource
import br.com.gj.giphytest.features.trending.TrendingViewModel
import br.com.gj.giphytest.features.trending.usecases.GetTrendingGifsUseCase
import br.com.gj.giphytest.features.trending.usecases.SearchGifsUseCase
import br.com.gj.giphytest.network.Api
import br.com.gj.giphytest.network.NetworkProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

object InjectionManager {

    val ioScheduler = named("IO_SCHEDULER")
    val mainThreadScheduler = named("MAIN_THREAD_SCHEDULER")

    fun init(application: Application) {
        startKoin {
            androidContext(application)
            modules(
                viewModelModule,
                useCasesModule,
                dataSourceModule,
                daoModule,
                networkModule,
                rxModule
            )
        }
    }

    private val viewModelModule = module {
        viewModel { TrendingViewModel(get(), get(), get(), get()) }
        viewModel { FavoritesViewModel(get(), get()) }
    }

    private val useCasesModule = module {
        factory { GetTrendingGifsUseCase(get(), get()) }
        factory { AddFavoriteGifUseCase(get()) }
        factory { RemoveFavoriteGifUseCase(get()) }
        factory { GetAllFavoritesGifUseCase(get()) }
        factory { SearchGifsUseCase(get(), get()) }
        factory { CombineWithFavoriteGifsUseCase(get()) }
    }

    private val dataSourceModule = module {
        factory { RemoteDataSource(get()) }
        factory { FavoritesLocalDataSource(get()) }
    }

    private val daoModule = module {
        single { DatabaseManager.getInstance().favoritesDao() }
    }

    private val networkModule = module {
        single { NetworkProvider.provideOkHttpClient() }
        single<Api> { NetworkProvider.provideRetrofit(get()) }
    }

    private val rxModule = module {
        factory(ioScheduler) { Schedulers.io() }
        factory(mainThreadScheduler) { AndroidSchedulers.mainThread() }
    }
}
