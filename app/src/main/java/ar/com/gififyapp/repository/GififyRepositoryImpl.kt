package ar.com.gififyapp.repository

import ar.com.gififyapp.core.Result
import ar.com.gififyapp.data.local.LocalGififyDataSource
import ar.com.gififyapp.data.model.Gifify
import ar.com.gififyapp.data.model.GififyFavorite
import ar.com.gififyapp.data.model.GififyList
import ar.com.gififyapp.data.remote.RemoteGififyDataSource

/**
 * Created by Fernando Moreno on 12/9/2021.
 */
class GififyRepositoryImpl(
    private val dataSourceRemote: RemoteGififyDataSource,
    private val dataSourceLocal: LocalGififyDataSource
): GififyRepository {
    override suspend fun getGififys(): GififyList {
        return dataSourceRemote.getGififys()
    }

    override suspend fun getGififyList(name: String): Result<List<Gifify>> = dataSourceRemote.getGififyByName(name)

    override suspend fun getTrendingGifs(): GififyList = dataSourceRemote.getTrandingGifs()

    override suspend fun getFavoritesGifify(): Result<List<GififyFavorite>> = dataSourceLocal.getFavoritesGifify()

    override suspend fun saveFavoriteGifify(gififyFavorite: GififyFavorite) {
        dataSourceLocal.saveFavorite(gififyFavorite)
    }

    override suspend fun deleteFavoriteGifify(gififyFavorite: GififyFavorite) {
        dataSourceLocal.deleteFavoriteGifify(gififyFavorite)
    }

    override suspend fun isGififyFavorite(gififyFavorite: GififyFavorite): Boolean = dataSourceLocal.isGififyFavorite(gififyFavorite)

}