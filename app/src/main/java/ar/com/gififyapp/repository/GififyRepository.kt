package ar.com.gififyapp.repository

import ar.com.gififyapp.core.Result
import ar.com.gififyapp.data.model.Gifify
import ar.com.gififyapp.data.model.GififyFavorite
import ar.com.gififyapp.data.model.GififyList

/**
 * Created by Fernando Moreno on 12/9/2021.
 */
interface GififyRepository {

    suspend fun getGififys(): GififyList
    suspend fun getGififyList(name: String): Result<List<Gifify>>

    suspend fun getTrandingGifs(): GififyList

    suspend fun getFavoritesGifify(): Result<List<GififyFavorite>>
    suspend fun saveFavoriteGifify(gififyFavorite: GififyFavorite)
    suspend fun deleteFavoriteGifify(gififyFavorite: GififyFavorite)
    suspend fun isGififyFavorite(gififyFavorite: GififyFavorite): Boolean
}