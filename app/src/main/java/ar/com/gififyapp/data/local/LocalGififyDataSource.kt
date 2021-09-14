package ar.com.gififyapp.data.local

import ar.com.gififyapp.core.Result
import ar.com.gififyapp.data.model.GififyFavorite

/**
 * Created by Fernando Moreno on 13/9/2021.
 */
class LocalGififyDataSource(private val gififyDao: GififyDao) {

    suspend fun saveFavorite(gifify: GififyFavorite){
        gififyDao.saveFavoriteGifify(gifify)
    }

    suspend fun deleteFavoriteGifify(gifify: GififyFavorite){
        gififyDao.deleteFavoriteGifify(gifify)
    }

    suspend fun getFavoritesGifify(): Result<List<GififyFavorite>> = Result.Success(gififyDao.getAllFavoritesGifify())

    suspend fun isGififyFavorite(gifify: GififyFavorite): Boolean = gififyDao.getGififyById(gifify.id) != null
}