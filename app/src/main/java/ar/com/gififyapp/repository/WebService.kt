package ar.com.gififyapp.repository

import ar.com.gififyapp.data.model.GififyList
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Fernando Moreno on 12/9/2021.
 */
interface WebService {

    @GET
    suspend fun getGififys(): GififyList

    @GET("gifs/search")
    suspend fun getGififysByName(@Query("api_key") apiKey: String, @Query("q") name: String): GififyList

    @GET("gifs/trending")
    suspend fun getTrandingGifs(@Query("api_key") apiKey: String): GififyList
}