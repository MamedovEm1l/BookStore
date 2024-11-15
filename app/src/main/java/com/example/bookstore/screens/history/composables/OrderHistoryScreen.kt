package com.example.bookstore.screens.history.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.bookstore.data.Order
import com.example.bookstore.ui.theme.CreamColor
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun OrderListItemUi(
    order: Order,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
    ) {
        Row(
            modifier = Modifier.background(CreamColor)
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            // Product Image
            Image(
                painter = rememberAsyncImagePainter(order.books.get(1).imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 8.dp),
                contentScale = ContentScale.Crop
            )

            // Product Details
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "№ ${order.id}",
                    fontSize = 14.sp,
                    color = Color.Blue
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = order.books.get(1).title,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${order.books.get(1).price}₽",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "${order.discountPrice}₽",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = order.timeAgo, // e.g., "7 мин назад"
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
