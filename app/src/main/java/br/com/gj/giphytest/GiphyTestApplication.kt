package br.com.gj.giphytest

import android.app.Application
import br.com.gj.giphytest.data.DatabaseManager
import br.com.gj.giphytest.di.InjectionManager

class GiphyTestApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        DatabaseManager.init(this)
        InjectionManager.init(this)
    }
}