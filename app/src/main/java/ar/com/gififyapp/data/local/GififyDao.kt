package ar.com.gififyapp.data.local

import androidx.room.*
import ar.com.gififyapp.data.model.GififyFavorite

/**
 * Created by Fernando Moreno on 13/9/2021.
 */
@Dao
interface GififyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavoriteGifify(gifify: GififyFavorite)

    @Delete
    suspend fun deleteFavoriteGifify(gifify: GififyFavorite)

    @Query("SELECT * FROM favorites")
    suspend fun getAllFavoritesGifify(): List<GififyFavorite>

    @Query("SELECT * FROM favorites WHERE id = :id")
    suspend fun getGififyById(id: String): GififyFavorite
}