package ar.com.gififyapp.data.local

import ar.com.gififyapp.core.Result
import ar.com.gififyapp.data.model.GififyFavorite
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

/**
 * Created by Fernando Moreno on 13/9/2021.
 */
@ExperimentalCoroutinesApi
class LocalGififyDataSource @Inject constructor(private val gififyDao: GififyDao) {

    suspend fun saveFavorite(gifify: GififyFavorite){
        gififyDao.saveFavoriteGifify(gifify)
    }

    suspend fun deleteFavoriteGifify(gifify: GififyFavorite){
        gififyDao.deleteFavoriteGifify(gifify)
    }

    suspend fun getFavoritesGifify(): Result<List<GififyFavorite>> = Result.Success(gififyDao.getAllFavoritesGifify())

    suspend fun isGififyFavorite(gifify: GififyFavorite): Boolean = gififyDao.getGififyById(gifify.id) != null
}