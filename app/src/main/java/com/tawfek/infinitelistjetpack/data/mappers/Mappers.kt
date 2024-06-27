package com.tawfek.infinitelistjetpack.data.mappers

import com.tawfek.infinitelistjetpack.data.post.local.PostEntity
import com.tawfek.infinitelistjetpack.data.post.remote.PostDto
import com.tawfek.infinitelistjetpack.domain.post.model.Post
import com.tawfek.infinitelistjetpack.domain.use_cases.ErrorType
import com.tawfek.infinitelistjetpack.domain.use_cases.HandleServerErrorUseCase
import retrofit2.HttpException
import java.io.IOException

fun PostDto.toPostEntity() = PostEntity(0, id, tags, imageUrl, views, downloads, likes, comments, userId, userName, userImageURL)

fun PostEntity.toPostDto() = PostDto(id, tags, imageUrl, views, downloads, likes, comments, userId, userName, userImageURL)

fun PostEntity.toPost() = Post(id, tags, imageUrl, views, downloads, likes, comments, userId, userName, userImageURL)

fun Exception.getErrorType() : ErrorType{
    return if (this is IOException){
        ErrorType.NetworkNotAvailable
    }else if (this is HttpException){
        HandleServerErrorUseCase().getErrorType(this.code())
    }else{
        ErrorType.UnknownError
    }
}