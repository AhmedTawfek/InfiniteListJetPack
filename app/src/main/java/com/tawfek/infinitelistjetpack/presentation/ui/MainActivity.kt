package com.tawfek.infinitelistjetpack.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.tawfek.infinitelistjetpack.presentation.ui.screen.home.HomeScreen
import com.tawfek.infinitelistjetpack.presentation.ui.screen.home.HomeViewModel
import com.tawfek.infinitelistjetpack.presentation.ui.theme.InfiniteListJetPackTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InfiniteListJetPackTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    val viewModel = hiltViewModel<HomeViewModel>()
                    val posts = viewModel.postsPagerFlow.collectAsLazyPagingItems()
                    Log.d("Pagination","${posts.itemCount}")
                    HomeScreen(posts = posts)
                }
            }
        }
    }
}