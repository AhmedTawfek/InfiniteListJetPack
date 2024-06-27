package com.tawfek.infinitelistjetpack.domain.post.model

data class Post(
    val id: Int = 0,
    val tags: String = "",
    val imageUrl: String = "",
    val views: Int = 0,
    val downloads: Int = 0,
    val likes: Int = 0,
    val comments: Int = 0,
    val userId: Int = 0,
    val userName: String = "",
    val userImageURL: String = ""
)
