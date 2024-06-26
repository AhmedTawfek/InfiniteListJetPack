package com.tawfek.infinitelistjetpack.domain.post.model

data class Post(
    val id: Int,
    val tags: String,
    val imageUrl: String,
    val views: Int,
    val downloads: Int,
    val likes: Int,
    val comments: Int,
    val userId: Int,
    val userName: String,
    val userImageURL: String
)
