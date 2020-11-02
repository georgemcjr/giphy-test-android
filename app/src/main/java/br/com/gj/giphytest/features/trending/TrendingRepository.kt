package br.com.gj.giphytest.features.trending

class TrendingRepository(
    private val remoteDataSource: TrendingRemoteDataSource
) {

    fun fetchTrendingGifList() = remoteDataSource.fetchTrendingGifList()

}
