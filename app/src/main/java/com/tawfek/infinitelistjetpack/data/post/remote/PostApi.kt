package com.tawfek.infinitelistjetpack.data.post.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface PostApi {
    @GET("api/")
    suspend fun searchForPosts(
        @Query("key") apiKey: String,
        @Query("q") query: String,
        @Query("image_type") imageType : String = "photo",
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ) : List<PostDto>

    companion object{
        const val BASE_URL = "https://pixabay.com/"
    }
}