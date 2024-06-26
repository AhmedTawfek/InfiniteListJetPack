package com.tawfek.infinitelistjetpack.data.post.remote

import com.google.gson.annotations.SerializedName

data class PostResponse(
    val total: Int,
    val totalHits: Int,
    @SerializedName("hits") val posts: List<PostDto>)

data class PostDto(
    val id: Int,
    val tags: String,
    @SerializedName("largeImageURL") val imageUrl: String,
    val views: Int,
    val downloads: Int,
    val likes: Int,
    val comments: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("user") val userName: String,
    val userImageURL: String
)