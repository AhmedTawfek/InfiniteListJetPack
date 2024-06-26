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
            val postsDao = localDatabase.postDao()

            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1

                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()

                    Log.d("Pagination", "lastItemId is: ${lastItem?.id}")

                    if (lastItem == null) {
                        1
                    }else{
                        val totalPostsCountExistsLocally = postsDao.getPostsCount()

                        if(totalPostsCountExistsLocally == 0) {
                            1
                        }else{
                            (totalPostsCountExistsLocally / state.config.pageSize) + 1
                        }
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

            localDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    localDatabase.postDao().clearPosts()
                }

                val postEntities = postsResponse.map {
                    it.toPostEntity()
                }

                localDatabase.postDao().upsertPosts(postEntities)
            }

            return MediatorResult.Success(endOfPaginationReached = postsResponse.isEmpty())
        } catch (ioException: IOException) {
            return MediatorResult.Error(ioException)
        } catch (httpException: HttpException) {
            return MediatorResult.Error(httpException)
        }
    }
}