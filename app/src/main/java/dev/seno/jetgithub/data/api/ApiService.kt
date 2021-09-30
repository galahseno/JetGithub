package dev.seno.jetgithub.data.api

import dev.seno.jetgithub.BuildConfig
import dev.seno.jetgithub.data.model.DetailUser
import dev.seno.jetgithub.data.model.ListUser
import dev.seno.jetgithub.data.model.User
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers(BuildConfig.GITHUB_TOKEN)
    suspend fun getListUsers(@Query("q") q: String): ListUser

    @GET("users/{username}")
    @Headers(BuildConfig.GITHUB_TOKEN)
    suspend fun getDetailUsers(@Path("username") username: String): DetailUser

    @GET("users/{username}/followers")
    @Headers(BuildConfig.GITHUB_TOKEN)
    suspend fun getListFollower(@Path("username") username: String): List<User>

    @GET("users/{username}/following")
    @Headers(BuildConfig.GITHUB_TOKEN)
    suspend fun getListFollowing(@Path("username") username: String): List<User>
}