package dev.seno.jetgithub.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class ListUser(
    @SerializedName("items")
    val listUser: ArrayList<User>
)

@Parcelize
data class User(
    @SerializedName("avatar_url")
    val userImg: String,

    @SerializedName("login")
    val username: String,

    @SerializedName("id")
    val id: Int
) : Parcelable