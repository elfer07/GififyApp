package ar.com.gififyapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ar.com.gififyapp.application.Constants
import ar.com.gififyapp.data.model.GififyFavorite

/**
 * Created by Fernando Moreno on 13/9/2021.
 */
@Database(entities = [GififyFavorite::class], version = 1, exportSchema = false)
abstract class LocalDatabase: RoomDatabase() {

    abstract fun gififyDao(): GififyDao


}