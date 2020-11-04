package br.com.gj.giphytest.model

import androidx.room.*

@Entity(tableName = "favorites", indices = [Index(value = ["id"])])
data class GifItem( // TODO rename to more generic name
    @PrimaryKey val id: String,
    @ColumnInfo(name = "gif_url") val gifUrl: String,
    @Ignore val isFavorite: Boolean = false
)