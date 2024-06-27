package com.tawfek.infinitelistjetpack.data.post.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val localId: Int,
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