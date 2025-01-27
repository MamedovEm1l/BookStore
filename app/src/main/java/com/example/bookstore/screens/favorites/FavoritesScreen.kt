package com.example.bookstore.screens.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bookstore.data.Book
import com.example.bookstore.screens.favorites.composables.FavBookListItemUi
import com.example.bookstore.screens.main_screen.Composables.BottomMenu
import com.example.bookstore.screens.main_screen.TopBar
import com.example.bookstore.ui.theme.ButtonColor
import com.example.bookstore.ui.theme.CreamColor
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun FavoritesScreen(
    favorites: List<Book>,
    onBookClick: (Book) -> Unit,
    onRemoveFromFavorites: (Book) -> Unit,
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
                .fillMaxSize()
                .background(ButtonColor)
                .padding(paddingValues)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Избранное",
                color = CreamColor,
                fontSize = 24.sp
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
            ){
                items(bookListState.value) { book ->
                    FavBookListItemUi(book = book, onClick = { onBookClick(book) })
                    Spacer(modifier = Modifier.height(10.dp))
                }
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