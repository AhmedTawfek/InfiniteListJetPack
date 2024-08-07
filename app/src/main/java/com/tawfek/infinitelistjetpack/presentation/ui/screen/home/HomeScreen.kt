@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.tawfek.infinitelistjetpack.presentation.ui.screen.home

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.tawfek.infinitelistjetpack.R
import com.tawfek.infinitelistjetpack.data.mappers.getErrorType
import com.tawfek.infinitelistjetpack.domain.post.model.Post
import com.tawfek.infinitelistjetpack.presentation.ui.AppEntry
import com.tawfek.infinitelistjetpack.presentation.ui.common.IconWithText
import com.tawfek.infinitelistjetpack.presentation.ui.theme.InfiniteListJetPackTheme
import com.tawfek.infinitelistjetpack.presentation.ui.utils.convertErrorTypeToMessage

@Composable
fun SharedTransitionScope.HomeScreen(posts: LazyPagingItems<Post>, animatedVisibilityScope: AnimatedVisibilityScope, onItemClick : (Post) -> Unit) {

    val context = LocalContext.current

    LaunchedEffect(key1 = posts.loadState) {
        if (posts.loadState.refresh is LoadState.Error) {
            val errorType = ((posts.loadState.refresh as LoadState.Error).error as Exception).getErrorType()

            Toast.makeText(
                context,
                errorType.convertErrorTypeToMessage(context),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()) {
        if (posts.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {

            val listState = rememberLazyListState()

            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {

                items(posts){postItem ->
                    if (postItem!=null){
                        PostCard(post = postItem,animatedVisibilityScope){post ->
                            onItemClick(post)
                        }
                    }
                }

                item {
                    if (posts.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun SharedTransitionScope.PostCard(post: Post,animatedVisibilityScope: AnimatedVisibilityScope,onItemClick: (Post) -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .clickable { onItemClick(post) }) {
        Row(modifier = Modifier.fillMaxWidth()) {

            AsyncImage(
                model = post.userImageURL, contentDescription = null, modifier = Modifier
                    .sharedElement(
                        state = rememberSharedContentState(key = post.userImageURL),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            tween(durationMillis = 1000)
                        }
                    )
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.profile_icon),
                error = painterResource(id = R.drawable.profile_icon)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = post.userName, modifier = Modifier.align(Alignment.CenterVertically))
        }

        Spacer(modifier = Modifier.height(10.dp))

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(post.imageUrl)
                .crossfade(false)
                .placeholderMemoryCacheKey(post.imageUrl) //  same key as shared element key
                .memoryCacheKey(post.imageUrl) // same key as shared element key
                .build(), contentDescription = null, modifier = Modifier
                .sharedElement(
                    state = rememberSharedContentState(key = post.imageUrl),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { _, _ ->
                        tween(durationMillis = 1000)
                    }
                )
                .fillMaxWidth()
                .height(140.dp)
                .clip(RoundedCornerShape(10.dp))
            , contentScale = ContentScale.FillBounds, placeholder = painterResource(id = R.drawable.pixeled_background), error = painterResource(
                id = R.drawable.pixeled_background
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            IconWithText(icon = R.drawable.like_icon, text = "${post.likes}")
            IconWithText(icon = R.drawable.comment_icon, text = "${post.comments}")
            IconWithText(icon = R.drawable.download_icon, text = "${post.downloads}")
            IconWithText(icon = R.drawable.view_icon, text = "${post.views}")
        }
    }

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(2.dp)
        .background(Color(0xFFF6F6F6))) {
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPrev() {

    InfiniteListJetPackTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
        }
    }

}