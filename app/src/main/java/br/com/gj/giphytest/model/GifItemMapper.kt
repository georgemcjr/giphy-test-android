package br.com.gj.giphytest.model

object GifItemMapper {

    fun mapFromTrendingResponse(trendingResponse: TrendingResponse) =
        trendingResponse.data.map { responseItem ->
            GifItem(
                id = responseItem.id,
                gifUrl = responseItem.images.image.url
            )
        }

}