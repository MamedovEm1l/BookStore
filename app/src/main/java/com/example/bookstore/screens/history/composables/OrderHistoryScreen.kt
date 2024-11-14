package com.example.bookstore.screens.history.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookstore.data.Order
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun OrderListItemUi(
    order: Order,
    onClick: () -> Unit
) {
    val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Text(
            text = "Заказ от ${dateFormatter.format(order.date)}",
            fontSize = 16.sp
        )
        Text(
            text = "Общая стоимость: ${order.totalCost}₽",
            fontSize = 14.sp
        )
    }
}
