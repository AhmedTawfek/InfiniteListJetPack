package com.tawfek.infinitelistjetpack.presentation.ui.screen.navigation

interface Screen{
    val route : String
    val title : String
}

object Home : Screen{
    override val route = "home"
    override val title = "Home"
}

object Details : Screen{
    override val route = "details"
    override val title = "Details"
}