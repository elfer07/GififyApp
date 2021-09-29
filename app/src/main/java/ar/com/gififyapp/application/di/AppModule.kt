package ar.com.gififyapp.application.di

import android.content.Context
import androidx.room.Room
import ar.com.gififyapp.application.Constants
import ar.com.gififyapp.application.Constants.BASE_URL
import ar.com.gififyapp.data.local.LocalDatabase
import ar.com.gififyapp.repository.WebService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Fernando Moreno on 29/9/2021.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRoomInstance(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        LocalDatabase::class.java,
        Constants.TABLE
    ).build()

    @Singleton
    @Provides
    fun provideGififyDao(db: LocalDatabase) = db.gififyDao()

    @Singleton
    @Provides
    fun provideRetrofitInstance() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    @Singleton
    @Provides
    fun provideWebService(retrofit: Retrofit) = retrofit.create(WebService::class.java)
}