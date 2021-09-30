package dev.seno.jetgithub.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.seno.jetgithub.utils.Constants.FAVORITE_TABLE_NAME
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = FAVORITE_TABLE_NAME)
data class FavoriteUser(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userName: String,
    val userImg: String,
    val name: String,
    val company: String,
    val location: String
) : Parcelable {

    fun toUser(): User {
        return User(
            id = id,
            username = userName,
            userImg = userImg
        )
    }
}