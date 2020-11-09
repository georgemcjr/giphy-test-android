package br.com.gj.giphytest.network

import br.com.gj.giphytest.features.common.model.GifListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("trending")
    fun fetchTrendingGifList() : Single<GifListResponse>

    @GET("search")
    fun searchGifList(@Query("q") query: String) : Single<GifListResponse>

}