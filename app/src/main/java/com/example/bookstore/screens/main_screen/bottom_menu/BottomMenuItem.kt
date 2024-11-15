package com.example.bookstore.screens.main_screen.bottom_menu

import com.example.bookstore.R
import com.example.bookstore.navigation.Screen

sealed class BottomMenuItem(
    val route: String,
    val title: String,
    val iconId: Int
) {
    object Home : BottomMenuItem(
        route = Screen.Main.route,
        title = "Home",
        iconId = R.drawable.ic_home
    )

    object Fav : BottomMenuItem(
        route = Screen.Favorites.route,
        title = "Favs",
        iconId = R.drawable.ic_fav
    )

    object Settings : BottomMenuItem(
        route = Screen.Favorites.route,
        title = "Settings",
        iconId = R.drawable.ic_settings
    )

    object Profile : BottomMenuItem(
        route = Screen.Profile.route,
        title = "Profile",
        iconId = R.drawable.ic_profile
    )

    object Orders : BottomMenuItem(
        route = Screen.OrderHistory.route,
        title = "Orders",
        iconId = R.drawable.store
    )
}