package ar.com.gififyapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Fernando Moreno on 12/9/2021.
 */
@Parcelize
data class User(
    val avatar_url: String,
    val banner_url: String,
    val profile_url: String,
    val username: String,
    val display_name: String,
    val description: String,
    val instagram_url: String,
    val website_url: String,
    val is_verified: Boolean
) : Parcelable