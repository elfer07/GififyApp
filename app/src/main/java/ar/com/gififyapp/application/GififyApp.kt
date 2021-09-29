package ar.com.gififyapp.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Fernando Moreno on 29/9/2021.
 */
@HiltAndroidApp
class GififyApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}