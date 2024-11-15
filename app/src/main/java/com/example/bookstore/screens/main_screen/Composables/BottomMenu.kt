package com.example.bookstore.screens.main_screen.Composables

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.bookstore.screens.main_screen.bottom_menu.BottomMenuItem
import com.example.bookstore.ui.theme.ButtonColor
import com.example.bookstore.ui.theme.CreamColor

@Composable
fun BottomMenu(navController: NavController) {
    val items = listOf(
        BottomMenuItem.Home,
        BottomMenuItem.Settings,
        BottomMenuItem.Fav,
        BottomMenuItem.Profile,
        BottomMenuItem.Orders
    )

    val selectedItem = remember { mutableStateOf("Home") }

    NavigationBar(containerColor = CreamColor, contentColor = ButtonColor) {
        items.forEach { item ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(ButtonColor),
                selected = false,
                onClick = {
                    selectedItem.value = item.title
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
