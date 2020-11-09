package br.com.gj.giphytest.network

import br.com.gj.giphytest.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    private val apiKeyParam = "api_key"
    private val apiKeyValue = BuildConfig.API_KEY

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val urlWithApiKey = original.url.newBuilder()
            .addQueryParameter(apiKeyParam, apiKeyValue)
            .build()

        val requestWithApiKey = original.newBuilder().url(urlWithApiKey).build()

        return chain.proceed(requestWithApiKey)
    }
}