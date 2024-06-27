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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tawfek.infinitelistjetpack.R
import com.tawfek.infinitelistjetpack.domain.post.model.Post
import com.tawfek.infinitelistjetpack.presentation.ui.theme.InfiniteListJetPackTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.DetailsScreen(post : Post,animatedVisibilityScope: AnimatedVisibilityScope,modifier: Modifier = Modifier) {

    Column(modifier = modifier) {

        Box(modifier = Modifier.height(250.dp)){

            AsyncImage(
                model = post.imageUrl, contentDescription = null, modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .sharedElement(
                        state = rememberSharedContentState(key = post.imageUrl),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            tween(durationMillis = 1000)
                        }
                    ),
                contentScale = ContentScale.FillBounds, placeholder = painterResource(id = R.drawable.pixeled_background),
                error = painterResource(
                    id = R.drawable.pixeled_background
                )
            )
            AsyncImage(
                model = post.userImageURL, contentDescription = null, modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .sharedElement(
                        state = rememberSharedContentState(key = post.userImageURL),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            tween(durationMillis = 1000)
                        }
                    )
                    .renderInSharedTransitionScopeOverlay(zIndexInOverlay = 1f)
                    .border(width = 2.dp, color = Color.White, shape = CircleShape)
                    .clip(CircleShape)
                    .size(120.dp),
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