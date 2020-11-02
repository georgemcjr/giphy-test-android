package br.com.gj.giphytest

import android.app.Application
import br.com.gj.giphytest.di.InjectionManager

class GiphyTestApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        InjectionManager.init(this)
    }
}