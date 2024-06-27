package com.tawfek.infinitelistjetpack.presentation.ui.screen.details

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tawfek.infinitelistjetpack.R
import com.tawfek.infinitelistjetpack.domain.post.model.Post
import com.tawfek.infinitelistjetpack.presentation.ui.theme.InfiniteListJetPackTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.DetailsScreen(post : Post,animatedVisibilityScope: AnimatedVisibilityScope,modifier: Modifier = Modifier) {

    Column(modifier = modifier) {

        Box(modifier = Modifier.height(250.dp)){

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
                    .renderInSharedTransitionScopeOverlay(renderInOverlay = {false}, zIndexInOverlay = 2f)
                    .fillMaxWidth()
                    .height(200.dp)
                    ,
                contentScale = ContentScale.FillBounds, placeholder = painterResource(id = R.drawable.pixeled_background),
                error = painterResource(
                    id = R.drawable.pixeled_background
                )
            )

            AsyncImage(
                model = post.userImageURL, contentDescription = null, modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.BottomCenter)
                    .sharedElement(
                        state = rememberSharedContentState(key = post.userImageURL),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            tween(durationMillis = 1000)
                        }, zIndexInOverlay = 1f
                    )
                    .border(width = 2.dp, color = Color.White, shape = CircleShape)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.profile_icon),
                error = painterResource(id = R.drawable.profile_icon)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDetailsScreen() {

    InfiniteListJetPackTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            //DetailsScreen(post =Post(1,"","",100,100,100,100,100,"Ahmed Tawfek","") )
        }
    }

}