package br.com.gj.giphytest.model

import com.google.gson.annotations.SerializedName

data class GifListResponse(
    val data: List<Items>
    // TODO create model for pagination
) {
    data class Items(
        val id: String,
        val images: ImageWrapper
    )

    data class ImageWrapper(
        @SerializedName("fixed_width") val image: Image
    )

    // TODO get the gif dimensions and redimension imageview to wrap the entire gif
    data class Image(
        val url: String
    )
}