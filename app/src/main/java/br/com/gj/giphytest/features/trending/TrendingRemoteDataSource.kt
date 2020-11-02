package br.com.gj.giphytest.features.trending

import br.com.gj.giphytest.Api

class TrendingRemoteDataSource(private val api: Api) {

    fun fetchTrendingGifList() = api.fetchTrendingGifList()
}
