package br.com.gj.giphytest.model

object TrendingItemMapper {

    fun mapFromResponse(trendingResponse: TrendingResponse) =
        trendingResponse.data.map { responseItem ->
            TrendingItem(
                id = responseItem.id,
                gifUrl = responseItem.images.image.url
            )
        }

}