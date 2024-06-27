@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.tawfek.infinitelistjetpack.presentation.ui.screen.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.tawfek.infinitelistjetpack.presentation.ui.screen.details.DetailsScreen
import com.tawfek.infinitelistjetpack.presentation.ui.screen.home.HomeScreen
import com.tawfek.infinitelistjetpack.presentation.ui.screen.home.HomeViewModel

@Composable
fun Navigation(navHostController: NavHostController,modifier: Modifier = Modifier) {

    SharedTransitionLayout {
        NavHost(navController = navHostController, startDestination = Home.route) {

            composable(Home.route) {
                val viewModel = hiltViewModel<HomeViewModel>()
                val posts = viewModel.postsPagerFlow.collectAsLazyPagingItems()

                HomeScreen(posts = posts, this) { post ->
                    viewModel.setCurrentSelectedPost(post)
                    navHostController.navigate(Details.route)
                }
            }

            composable(Details.route) {

                val viewModel = hiltViewModel<HomeViewModel>(
                    navHostController.getBackStackEntry(Home.route) // Get ViewModel from Home screen's back stack entry
                )
                val currentSelectedPost = viewModel.currentSelectedPost.collectAsStateWithLifecycle()

                DetailsScreen(post = currentSelectedPost.value,this)
            }
        }
    }
}