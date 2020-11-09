package br.com.gj.giphytest.features.trending

import br.com.gj.giphytest.network.Api

class RemoteDataSource(private val api: Api) {

    fun fetchTrendingGifList() = api.fetchTrendingGifList()

    fun searchGifList(query: String) = api.searchGifList(query)
}
