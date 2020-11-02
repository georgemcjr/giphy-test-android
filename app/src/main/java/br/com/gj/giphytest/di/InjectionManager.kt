package br.com.gj.giphytest.di

import android.app.Application
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
                repositoryModule
            )
        }
    }

    private val viewModelModule = module {
        viewModel { TrendingViewModel(get()) }
    }

    private val repositoryModule = module {
        factory { TrendingRepository() }
    }

}