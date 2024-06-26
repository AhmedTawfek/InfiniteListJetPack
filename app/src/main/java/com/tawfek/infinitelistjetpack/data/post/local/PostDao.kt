package com.tawfek.infinitelistjetpack.data.post.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PostDao {
    @Upsert
    suspend fun upsertPosts(posts: List<PostEntity>)

    @Query("SELECT * FROM PostEntity")
    fun getPosts(): PagingSource<Int, PostEntity>

    @Query("SELECT COUNT(*) FROM PostEntity")
    suspend fun getPostsCount(): Int

    @Query("DELETE FROM PostEntity")
    suspend fun clearPosts()
}