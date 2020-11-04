package br.com.gj.giphytest.di

import android.app.Application
import br.com.gj.giphytest.Api
import br.com.gj.giphytest.features.favorites.*
import br.com.gj.giphytest.features.trending.GetTrendingGifsUseCase
import br.com.gj.giphytest.features.trending.TrendingRemoteDataSource
import br.com.gj.giphytest.features.trending.TrendingViewModel
import br.com.gj.giphytest.network.NetworkProvider
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

object InjectionManager {

    fun init(application: Application) {
        startKoin {
            androidContext(application)
            modules(
                viewModelModule,
                useCasesModule,
                dataSourceModule,
                networkModule
            )
        }
    }

    private val viewModelModule = module {
        viewModel { TrendingViewModel(get(), get(), get(), get()) }
        viewModel { FavoritesViewModel(get(), get()) }
    }

    private val useCasesModule = module {
        factory { GetTrendingGifsUseCase(get()) }
        factory { AddFavoriteGifUseCase(get()) }
        factory { RemoveFavoriteGifUseCase(get()) }
        factory { GetAllFavoritesGifUseCase(get()) }
    }

    private val dataSourceModule = module {
        factory { TrendingRemoteDataSource(get()) }
        factory { FavoritesLocalDataSource() }
    }

    private val networkModule = module {
        single { NetworkProvider.provideOkHttpClient() }

        single<Api> { NetworkProvider.provideRetrofit(get()) }
    }

}