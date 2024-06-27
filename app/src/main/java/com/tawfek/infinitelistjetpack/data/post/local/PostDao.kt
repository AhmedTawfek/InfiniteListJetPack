package com.tawfek.infinitelistjetpack.data.post.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PostDao {
    @Insert
    suspend fun upsertPosts(posts: List<PostEntity>)

    @Query("SELECT * FROM PostEntity order by localId asc")
    fun getPosts(): PagingSource<Int, PostEntity>

    @Query("SELECT COUNT(*) FROM PostEntity")
    suspend fun getPostsCount(): Int

    @Query("DELETE FROM PostEntity")
    suspend fun clearPosts()
}