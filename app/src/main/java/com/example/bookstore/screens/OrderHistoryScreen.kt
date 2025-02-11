package com.example.bookstore.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.bookstore.composables.BottomMenu
import com.example.bookstore.model.Order
import com.example.bookstore.screens.main_screen.bottom_menu.BottomMenuItem
import com.example.bookstore.ui.theme.ButtonColor
import com.example.bookstore.ui.theme.CreamColor
import com.example.bookstore.viewmodel.OrderViewModel

@Composable
fun OrderHistoryScreen(
    onOrderClick: (Order) -> Unit,
    orderViewModel: OrderViewModel,
    navController: NavHostController
) {
    val selectedBottomItemState = remember {
        mutableStateOf(BottomMenuItem.Orders.title)
    }

    LaunchedEffect(Unit) {
        orderViewModel.fetchOrders()
    }
    val orders by orderViewModel.orders.collectAsState()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamColor),
        bottomBar = {
            BottomMenu(navController = navController, selectedBottomItemState.value)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CreamColor)
                .padding(paddingValues)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Заказы",
                color = CreamColor,
                fontSize = 24.sp
            )

            LazyColumn(modifier = Modifier.fillMaxSize().background(ButtonColor).padding(paddingValues)) {
                items(orders) { order ->
                    OrderListItem(order = order, onClick = { onOrderClick(order) })
                }
            }
        }
    }
}

@Composable
fun OrderListItem(order: Order, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(containerColor = CreamColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = order.address,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = ButtonColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${order.time} ${order.date} ",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${"%.2f".format(order.totalPrice)}₽",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = ButtonColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (order.state) "Доставлен" else "Возврат",
                    fontSize = 16.sp,
                    color = if (order.state) Color(0xFF4CAF50) else Color.Red
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(order.items) { item ->
                AsyncImage(
                    model = item.book.imageUrl,
                    contentDescription = "Book Image",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(25.dp))
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}