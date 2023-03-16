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
    ): Call<List<UserFollowerResponseItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUserFollowing(
        @Path("username") login: String
    ): Call<List<UserFollowerResponseItem>>
}