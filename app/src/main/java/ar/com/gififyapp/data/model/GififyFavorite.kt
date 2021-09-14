package ar.com.gififyapp.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Fernando Moreno on 13/9/2021.
 */
@Entity(tableName = "favorites")
data class GififyFavorite(
    val type: String,
    @PrimaryKey
    val id: String,
    val urlOriginal: String,
    val usernameOriginal: String,
    val source: String,
    val title: String,
    @Embedded
    val images: ImageGifify,
    @Embedded
    val user: User?
)
/*
@Parcelize
@Entity
data class ImageGififyFavorite(
    @Embedded
    val original: OriginalFavorite
) : Parcelable

@Parcelize
data class OriginalFavorite(
    val urlOriginal: String
) : Parcelable*/