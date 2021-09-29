package ar.com.gififyapp.presentation


import androidx.lifecycle.*
import ar.com.gififyapp.application.ToastHelper
import ar.com.gififyapp.core.Result
import ar.com.gififyapp.data.model.GififyFavorite
import ar.com.gififyapp.repository.GififyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Fernando Moreno on 12/9/2021.
 */
@HiltViewModel
class GififyViewModel @Inject constructor(
    private val repo: GififyRepository,
    private val toastHelper: ToastHelper
): ViewModel() {

    private val gififyData = MutableLiveData<String>()

    fun setGif(name: String){
        gififyData.value = name
    }

    init {
        setGif("")
    }

    val fetchGififys = gififyData.distinctUntilChanged().switchMap {
        liveData(Dispatchers.IO) {
            emit(Result.Loading())
            try {
                emit(repo.getGififyList(it))
            } catch (e: Exception){
                emit(Result.Failure(e))
            }
        }
    }

    fun fetchTrandingGifs() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.getTrendingGifs()))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun getFavoritesGifify() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        kotlin.runCatching {
            repo.getFavoritesGifify()
        }.onSuccess {
            emit(it)
        }.onFailure {
            emit(Result.Failure(Exception(it.message)))
        }
    }

    fun saveOrDeleteFavoriteGifify(gififyFavorite: GififyFavorite){
        viewModelScope.launch {
            if (repo.isGififyFavorite(gififyFavorite)){
                repo.deleteFavoriteGifify(gififyFavorite)
                toastHelper.sendToast("Gif borrado de Favoritos")
            } else {
                repo.saveFavoriteGifify(gififyFavorite)
                toastHelper.sendToast("Gif agregado a Favoritos")
            }
        }
    }

    suspend fun isGififyFavorite(gififyFavorite: GififyFavorite): Boolean = repo.isGififyFavorite(gififyFavorite)

}