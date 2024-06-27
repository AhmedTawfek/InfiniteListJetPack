package com.tawfek.infinitelistjetpack.presentation.di

import android.content.Context
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.tawfek.infinitelistjetpack.data.post.local.LocalDatabase
import com.tawfek.infinitelistjetpack.data.post.local.PostEntity
import com.tawfek.infinitelistjetpack.data.post.remote.PostApi
import com.tawfek.infinitelistjetpack.data.post.remote.RemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context): LocalDatabase {
        return Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            "localdatabase.db"
        ).build()
    }

    @Provides
    @Singleton
    fun providePostApi(): PostApi {
        return Retrofit.Builder()
            .baseUrl(PostApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun providePager(localDatabase: LocalDatabase, postsApi: PostApi): Pager<Int, PostEntity> {
        return Pager(
            config = PagingConfig(pageSize = 15, initialLoadSize = 15, prefetchDistance = 0),
            remoteMediator = RemoteMediator(
                localDatabase = localDatabase,
                postApi = postsApi
            ),
            pagingSourceFactory = {
                Log.d("Pagination","pagingSourceFactory Getting data from local.")
                localDatabase.postDao().getPosts()
            }
        )
    }
}