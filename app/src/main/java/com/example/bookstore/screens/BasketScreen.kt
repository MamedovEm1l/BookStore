package com.example.bookstore.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.bookstore.R
import com.example.bookstore.composables.LoginButton
import com.example.bookstore.model.BasketItem
import com.example.bookstore.model.Screen
import com.example.bookstore.ui.theme.CreamColor
import com.example.bookstore.viewmodel.BasketViewModel
import kotlinx.coroutines.launch

@Composable
fun BasketScreen(
    basketViewModel: BasketViewModel,
    navController: NavController
) {
    val basket by basketViewModel.basket.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        basketViewModel.fetchBasket()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamColor)
            .padding(16.dp)
    ) {
        Text(
            text = "Корзина",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Список элементов корзины
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(basket) { basketItem ->
                BasketItem(
                    basketItem = basketItem,
                    onIncrease = {
                        basketViewModel.increaseQuantity(basketItem.book.key)
                        basketViewModel.saveBasket()
                    },
                    onDecrease = { basketViewModel.decreaseQuantity(basketItem.book.key) },
                    onRemove = { basketViewModel.removeBook(basketItem.book) }
                )
            }
        }

        val totalCost = remember(basket) {
            basket.sumOf {
                it.book.price.dropLast(2).toDoubleOrNull()?.times(it.quantity) ?: 0.0
            }
        }


        Text(
            text = "Итого: ${"%.2f".format(totalCost)} руб.",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LoginButton("Отмена", Modifier.weight(1f).padding(end = 10.dp)) {
                coroutineScope.launch {
                    basketViewModel.clearBasket()
                }
                navController.navigate(Screen.Main.route)
            }
            LoginButton("Оформить", Modifier.weight(1f).padding(start = 10.dp))
            {
                if (basket.isNotEmpty()) navController.navigate(Screen.OrderForm.route)
            }
        }
    }
}

@Composable
fun BasketItem(
    basketItem: BasketItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            AsyncImage(
                model = basketItem.book.imageUrl,
                contentDescription = "",
                modifier = Modifier
                    .size(100.dp)
            )
            Log.d("BasketScreen", "Image URL: ${basketItem.book}")

            Column {
                Text(
                    text = basketItem.book.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Цена: ${basketItem.book.price} руб.",
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }
        }

        // Управление количеством и удаление
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onRemove) {
                Icon(Icons.Default.Delete, contentDescription = "Удалить")
            }
            IconButton(onClick = onDecrease) {
                Icon(
                    modifier = Modifier.size(15.dp),
                    painter = painterResource(R.drawable.minus),
                    contentDescription = "Уменьшить"
                )
            }
            Text(
                text = "${basketItem.quantity}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = onIncrease) {
                Icon(Icons.Default.Add, contentDescription = "Увеличить")
            }

        }
    }
}
