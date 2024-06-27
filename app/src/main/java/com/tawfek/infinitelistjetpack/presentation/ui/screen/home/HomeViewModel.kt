package com.tawfek.infinitelistjetpack.presentation.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LOG_TAG
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.tawfek.infinitelistjetpack.data.mappers.toPost
import com.tawfek.infinitelistjetpack.data.post.local.PostEntity
import com.tawfek.infinitelistjetpack.domain.post.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
     pager : Pager<Int, PostEntity>
) : ViewModel() {

    private val _currentSelectedPost = MutableStateFlow(Post())
    val currentSelectedPost = _currentSelectedPost.asStateFlow()

    fun setCurrentSelectedPost(post: Post){
        _currentSelectedPost.value = post
    }

    val postsPagerFlow = pager.flow
        .map { pagingData ->
            pagingData.map { it.toPost() }
        }
        .cachedIn(viewModelScope)
}