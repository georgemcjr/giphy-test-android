package br.com.gj.giphytest.features.trending

import br.com.gj.giphytest.model.TrendingItemMapper

class TrendingRepository(
    private val remoteDataSource: TrendingRemoteDataSource
) {

    fun fetchTrendingGifList() = remoteDataSource.fetchTrendingGifList()
        .map {
            TrendingItemMapper.mapFromResponse(it)
        }

}
