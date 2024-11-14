package com.example.bookstore.screens.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookstore.data.Order
import com.example.bookstore.screens.history.composables.OrderListItemUi
import com.example.bookstore.ui.theme.ButtonColor

@Composable
fun OrderHistoryScreen(
    orders: List<Order>,
    onOrderClick: (Order) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "История заказов",
            color = ButtonColor,
            fontSize = 24.sp
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
        ){
            items(orders) { order ->
                OrderListItemUi(order = order, onClick = { onOrderClick(order) })
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}
