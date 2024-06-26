package com.tawfek.infinitelistjetpack.data.mappers

import com.tawfek.infinitelistjetpack.data.post.local.PostEntity
import com.tawfek.infinitelistjetpack.data.post.remote.PostDto
import com.tawfek.infinitelistjetpack.domain.post.model.Post

fun PostDto.toPostEntity() = PostEntity(id, tags, imageUrl, views, downloads, likes, comments, userId, userName, userImageURL)

fun PostEntity.toPostDto() = PostDto(id, tags, imageUrl, views, downloads, likes, comments, userId, userName, userImageURL)

fun PostEntity.toPost() = Post(id, tags, imageUrl, views, downloads, likes, comments, userId, userName, userImageURL)