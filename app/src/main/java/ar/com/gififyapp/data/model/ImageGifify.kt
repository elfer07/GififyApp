package ar.com.gififyapp.data.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

/**
 * Created by Fernando Moreno on 12/9/2021.
 */
@Parcelize
@Entity
data class ImageGifify(
    @Embedded
    val original: Original
) : Parcelable