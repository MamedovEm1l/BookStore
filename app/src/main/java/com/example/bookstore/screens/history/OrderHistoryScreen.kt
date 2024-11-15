package com.example.bookstore.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bookstore.data.Order
import com.example.bookstore.screens.history.composables.OrderListItemUi
import com.example.bookstore.screens.main_screen.Composables.BottomMenu
import com.example.bookstore.ui.theme.ButtonColor
import com.example.bookstore.ui.theme.CreamColor

@Composable
fun OrderHistoryScreen(
    recentOrders: List<Order>,
    previousOrders: List<Order>,
    onOrderClick: (Order) -> Unit,
    navController: NavController = NavController(context = TODO())
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamColor),
        bottomBar = {
            BottomMenu(navController = navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ButtonColor)
                .padding(paddingValues)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                text = "Заказы",
                fontSize = 24.sp,
                color = CreamColor,
                textAlign = TextAlign.Center
            )

            Text(text = "Недавний", fontSize = 18.sp, color = CreamColor)
            Spacer(modifier = Modifier.height(8.dp))
            recentOrders.forEach { order ->
                OrderListItemUi(order = order, onClick = { onOrderClick(order) })
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Вчера", fontSize = 18.sp, color = CreamColor)
            Spacer(modifier = Modifier.height(8.dp))
            previousOrders.forEach { order ->
                OrderListItemUi(order = order, onClick = { onOrderClick(order) })
            }
        }
    }
}
