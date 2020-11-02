package br.com.gj.giphytest.network

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    private val apiKeyParam = "api_key"
    private val apiKeyValue = "zixggTpunIccmTmv97n887ujCU5TprDa" // TODO: put this key on build.gradle

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val urlWithApiKey = original.url.newBuilder()
            .addQueryParameter(apiKeyParam, apiKeyValue)
            .build()

        val requestWithApiKey = original.newBuilder().url(urlWithApiKey).build()

        return chain.proceed(requestWithApiKey)
    }
}