package ar.com.gififyapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Fernando Moreno on 12/9/2021.
 */
@Parcelize
data class Original(
    val url: String
) : Parcelable