package br.com.gj.giphytest.model

object GifItemMapper {

    fun mapFromResponse(gifListResponse: GifListResponse) =
        gifListResponse.data.map { responseItem ->
            GifItem(
                id = responseItem.id,
                gifUrl = responseItem.images.image.url
            )
        }
}