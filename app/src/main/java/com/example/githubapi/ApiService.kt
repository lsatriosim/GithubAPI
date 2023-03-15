package com.example.githubapi
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUsers(
        @Query("q") q: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") login: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUserFollowers(
        @Path("username") login: String
    ): Call<UserFollowerResponse>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUserFollowing(
        @Path("username") login: String
    ): Call<UserFollowerResponse>
}