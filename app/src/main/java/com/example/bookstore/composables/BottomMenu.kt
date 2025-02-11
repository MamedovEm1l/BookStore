package com.example.bookstore.composables

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.bookstore.screens.main_screen.bottom_menu.BottomMenuItem
import com.example.bookstore.ui.theme.ButtonColor
import com.example.bookstore.ui.theme.CreamColor

@Composable
fun BottomMenu(
    navController: NavController,
    selectedItem: String = "Home"
) {
    val items = listOf(
        BottomMenuItem.Home,
        BottomMenuItem.Fav,
        BottomMenuItem.Orders,
        BottomMenuItem.Profile
    )


    NavigationBar(containerColor = CreamColor, contentColor = ButtonColor) {
        items.forEach { item ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(ButtonColor),
                selected = selectedItem == item.title,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconId),
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = item.title)
                }
            )
        }
    }
}
