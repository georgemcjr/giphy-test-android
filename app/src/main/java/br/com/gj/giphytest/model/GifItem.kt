package br.com.gj.giphytest.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "favorites", indices = [Index(value = ["id"])])
data class GifItem(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "gif_url") val gifUrl: String,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean = false
)