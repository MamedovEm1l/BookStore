package com.example.bookstore.screens.main_screen.bottom_menu

import com.example.bookstore.R

sealed class BottomMenuItem(
    val route: String,
    val title: String,
    val iconId: Int
) {
    object Home : BottomMenuItem(
        route = "",
        title = "Home",
        iconId = R.drawable.ic_home
    )

    object Fav : BottomMenuItem(
        route = "",
        title = "Favs",
        iconId = R.drawable.ic_fav
    )

    object Settings : BottomMenuItem(
        route = "",
        title = "Settings",
        iconId = R.drawable.ic_settings
    )
}