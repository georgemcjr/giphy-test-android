package br.com.gj.giphytest

import br.com.gj.giphytest.model.TrendingResponse
import io.reactivex.Single
import retrofit2.http.GET

interface Api {

    @GET("trending")
    fun fetchTrendingGifList() : Single<TrendingResponse>

}