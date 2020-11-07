package br.com.gj.giphytest

import br.com.gj.giphytest.model.GifListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("trending")
    fun fetchTrendingGifList() : Single<GifListResponse>

    @GET("search")
    fun searchGifList(@Query("q") query: String) : Single<GifListResponse>

}