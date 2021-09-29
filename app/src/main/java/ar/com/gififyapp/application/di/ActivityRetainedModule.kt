package ar.com.gififyapp.application.di

import ar.com.gififyapp.repository.GififyRepository
import ar.com.gififyapp.repository.GififyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

/**
 * Created by Fernando Moreno on 29/9/2021.
 */
@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {
    @Binds
    abstract fun dataSource(impl: GififyRepositoryImpl): GififyRepository
}