package ar.com.gififyapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ar.com.gififyapp.repository.GififyRepository

/**
 * Created by Fernando Moreno on 12/9/2021.
 */
class GififyViewModelFactory(private val repo: GififyRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = modelClass.getConstructor(GififyRepository::class.java).newInstance(repo)
}