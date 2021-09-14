package ar.com.gififyapp.data.model

/**
 * Created by Fernando Moreno on 12/9/2021.
 */
data class Gifify(
    val type: String = "",
    val id: String = "",
    val url: String = "",
    val embed_url: String = "",
    val username: String = "",
    val source: String = "",
    val title: String = "",
    val images: ImageGifify,
    val user: User?
)







