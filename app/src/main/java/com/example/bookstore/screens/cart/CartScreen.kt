package com.example.bookstore.screens.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookstore.data.Book
import com.example.bookstore.screens.favorites.composables.FavBookListItemUi
import com.example.bookstore.screens.main_screen.BookListItemUi
import com.example.bookstore.ui.theme.ButtonColor

@Composable
fun CartScreen(
    cartItems: List<Book>,
    onBookClick: (Book) -> Unit,
    onCheckout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Корзина",
            color = ButtonColor,
            fontSize = 24.sp
        )

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(cartItems) { book ->
                BookListItemUi(book = book, onClick = { onBookClick(book) })
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onCheckout,
            modifier = Modifier
                .fillMaxWidth()
                .padding( 40.dp)
        ) {
            Text(text = "Оформить заказ")
        }
    }
}
