package br.com.gj.giphytest.features.common.model

import com.google.gson.annotations.SerializedName

data class GifListResponse(
    val data: List<Items>
) {
    data class Items(
        val id: String,
        val images: ImageWrapper
    )

    data class ImageWrapper(
        @SerializedName("fixed_width") val image: Image
    )

    data class Image(
        val url: String
    )
}