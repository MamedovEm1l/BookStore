package com.example.bookstore.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bookstore.data.Book
import com.example.bookstore.screens.favorites.composables.FavBookListItemUi
import com.example.bookstore.screens.main_screen.BookListItemUi
import com.example.bookstore.screens.main_screen.Composables.BottomMenu
import com.example.bookstore.ui.theme.ButtonColor
import com.example.bookstore.ui.theme.CreamColor
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun CartScreen(
    cartItems: List<Book>,
    onBookClick: (Book) -> Unit,
    onCheckout: () -> Unit,
    navController: NavController = NavController(context = TODO())
) {
    val bookListState = remember {
        mutableStateOf(emptyList<Book>())
    }

    LaunchedEffect(Unit) {
        val db = Firebase.firestore
        getAllBooks(db) { books ->
            bookListState.value = books
        }
    }
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
                .fillMaxSize().background(ButtonColor)
                .padding(paddingValues)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Корзина",
                color = CreamColor,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(bookListState.value) { book ->
                    BookListItemUi(book = book, onClick = { onBookClick(book) })
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onCheckout,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding( 40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CreamColor
                )
            ) {
                Text(text = "Оформить заказ", color = ButtonColor)
            }
        }
    }
}

private fun getAllBooks(
    db: FirebaseFirestore,
    onBooks: (List<Book>) -> Unit
) {
    db.collection("books")
        .get()
        .addOnSuccessListener { task ->
            onBooks(task.toObjects(Book::class.java))
        }
        .addOnFailureListener {

        }
}