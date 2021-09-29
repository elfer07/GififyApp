package ar.com.gififyapp.data.remote

import ar.com.gififyapp.application.Constants.API_KEY
import ar.com.gififyapp.core.Result
import ar.com.gififyapp.data.model.Gifify
import ar.com.gififyapp.data.model.GififyList
import ar.com.gififyapp.repository.RetrofitClient
import ar.com.gififyapp.repository.WebService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

/**
 * Created by Fernando Moreno on 12/9/2021.
 */
@ExperimentalCoroutinesApi
class RemoteGififyDataSource @Inject constructor(private val webService: WebService) {

    suspend fun getGififys(): GififyList = webService.getGififys()
    suspend fun getGififyByName(name: String): Result<List<Gifify>> = Result.Success(RetrofitClient.webservice.getGififysByName(API_KEY, name).data)

    suspend fun getTrandingGifs(): GififyList = webService.getTrendingGifs(API_KEY)
}