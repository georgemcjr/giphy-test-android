package br.com.gj.giphytest

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET

interface Api {

    @GET("trending")
    fun fetchTrendingGifList() : Single<ResponseBody>

}