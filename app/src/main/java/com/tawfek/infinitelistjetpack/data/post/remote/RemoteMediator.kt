package com.tawfek.infinitelistjetpack.data.post.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.tawfek.infinitelistjetpack.data.mappers.toPostEntity
import com.tawfek.infinitelistjetpack.data.post.local.LocalDatabase
import com.tawfek.infinitelistjetpack.data.post.local.PostEntity
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class RemoteMediator(
    private val localDatabase: LocalDatabase,
    private val postApi: PostApi
) : RemoteMediator<Int, PostEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PostEntity>
    ): MediatorResult {
        try {
            delay(500)

            val postsDao = localDatabase.postDao()
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    Log.d("Pagination", "LoadType is Refresh")
                    1
                }

                LoadType.PREPEND -> {
                    Log.d("Pagination", "LoadType is Prepend")
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    Log.d("Pagination", "LoadType is Append")
                    val lastItem = state.lastItemOrNull()

                    if (lastItem == null) {
                        Log.d("Pagination", "No items found. Setting loadKey to 1.")
                        1
                    } else {
                        val totalPostsCountExistsLocally = postsDao.getPostsCount()
                        Log.d("Pagination", "Total posts count locally: $totalPostsCountExistsLocally")
                        val loadKey = (totalPostsCountExistsLocally / state.config.pageSize) + 1
                        Log.d("Pagination", "Computed loadKey: $loadKey")
                        loadKey
                    }
                }
            }

            Log.d("Pagination", "loadKey is: $loadKey")

            val postsResponse = postApi.searchForPosts(
                apiKey = "44587937-83eb06e8eec208ac26b62eff5",
                query = "yellow+flowers",
                page = loadKey,
                perPage = state.config.pageSize
            )

            Log.d("Pagination", "ResponseResult = $postsResponse")

            localDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    localDatabase.postDao().clearPosts()
                }

                val postEntities = postsResponse.posts.map { it.toPostEntity() }
                localDatabase.postDao().upsertPosts(postEntities)
            }

            return MediatorResult.Success(endOfPaginationReached = postsResponse.posts.isEmpty())
        } catch (exception : Exception) {
            Log.d("Pagination", "Exception In ResponseResult =${exception.message}")
            return MediatorResult.Error(exception)
        }
    }
}