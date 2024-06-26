package com.tawfek.infinitelistjetpack.data.post.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PostEntity::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}