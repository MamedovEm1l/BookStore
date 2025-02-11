package com.example.bookstore.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.bookstore.composables.LoginButton
import com.example.bookstore.model.Book
import com.example.bookstore.ui.theme.ButtonColor
import com.example.bookstore.ui.theme.CreamColor
import com.example.bookstore.viewmodel.BasketViewModel
import com.example.bookstore.viewmodel.FavoriteViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun BookDetailsScreen(
    bookId: String,
    basketViewModel: BasketViewModel,
    favoriteViewModel: FavoriteViewModel
) {
    val firestore = Firebase.firestore
    val bookState = remember { mutableStateOf<Book?>(null) }

    LaunchedEffect(bookId) {
        firestore.collection("books").document(bookId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    bookState.value = document.toObject(Book::class.java)
                }
            }
            .addOnFailureListener {
                bookState.value = null
            }
    }

    val book = bookState.value
    if (book != null) {
        BookDetailsContent(
            book = book, basketViewModel = basketViewModel, favoriteViewModel = favoriteViewModel
        )
    } else {
        Text("Загрузка данных книги...")
    }
}

@Composable
fun BookDetailsContent(
    book: Book,
    basketViewModel: BasketViewModel,
    favoriteViewModel: FavoriteViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = book.title,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = ButtonColor
        )
        Spacer(modifier = Modifier.height(20.dp))
        AsyncImage(
            model = book.imageUrl,
            contentDescription = book.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start),
            text = "Автор: ${book.author}",
            fontSize = 25.sp,
            color = ButtonColor
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Описание: ${book.description}",
            fontSize = 23.sp,
            textAlign = TextAlign.Justify,
            color = ButtonColor
        )
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Цена: ${book.price}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        LoginButton(text = "Добавить в корзину") {

            basketViewModel.addBook(book)
            Log.d("BookScreen", "Image URL: ${book.imageUrl}")

        }
        Spacer(modifier = Modifier.height(5.dp))
        LoginButton(text = "Добавить в избранные") {
            favoriteViewModel.addBook(book)
        }
    }
}
