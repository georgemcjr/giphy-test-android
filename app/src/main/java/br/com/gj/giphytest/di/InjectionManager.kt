package br.com.gj.giphytest.di

import android.app.Application
import br.com.gj.giphytest.Api
import br.com.gj.giphytest.network.NetworkProvider
import br.com.gj.giphytest.features.trending.TrendingRemoteDataSource
import br.com.gj.giphytest.features.trending.TrendingRepository
import br.com.gj.giphytest.features.trending.TrendingViewModel
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
                repositoryModule,
                dataSourceModule,
                networkModule
            )
        }
    }

    private val viewModelModule = module {
        viewModel { TrendingViewModel(get()) }
    }

    private val repositoryModule = module {
        factory { TrendingRepository(get()) }
    }

    private val dataSourceModule = module {
        factory { TrendingRemoteDataSource(get()) }
    }

    private val networkModule = module {
        single { NetworkProvider.provideOkHttpClient() }

        single<Api> { NetworkProvider.provideRetrofit(get()) }
    }

}