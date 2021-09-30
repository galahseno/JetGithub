package dev.seno.jetgithub.data.model

import com.google.gson.annotations.SerializedName

data class DetailUser(
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("company")
    val company: String?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("login")
    val username: String?
) {

    fun toFavoriteUser(): FavoriteUser {
        return FavoriteUser(
            userName = username ?: "-",
            name = name ?: "-",
            userImg = avatarUrl ?: "-",
            company = company ?: "-",
            location = location ?: "-"
        )
    }
}
