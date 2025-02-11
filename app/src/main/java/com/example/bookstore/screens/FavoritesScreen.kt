package com.example.bookstore.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bookstore.composables.BottomMenu
import com.example.bookstore.composables.FavBookListItemUi
import com.example.bookstore.model.Book
import com.example.bookstore.screens.main_screen.bottom_menu.BottomMenuItem
import com.example.bookstore.ui.theme.ButtonColor
import com.example.bookstore.ui.theme.CreamColor
import com.example.bookstore.viewmodel.FavoriteViewModel
import kotlinx.coroutines.launch

@Composable
fun FavoritesScreen(
    favoriteViewModel: FavoriteViewModel,
    onBookClick: (Book) -> Unit,
    navController: NavController
) {
    val favorite by favoriteViewModel.favorite.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val selectedBottomItemState = remember {
        mutableStateOf(BottomMenuItem.Fav.title)
    }
    // Загружаем данные при первом запуске
    LaunchedEffect(Unit) {
        favoriteViewModel.fetchFavorites() // Сначала загружаем избранное
    }

    // Проверка на успешное сохранение
    LaunchedEffect(favorite) {
        if (favorite.isNotEmpty()) {
            favoriteViewModel.saveFavorites() // Сохраняем только если есть избранные книги
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamColor),
        bottomBar = {
            BottomMenu(navController = navController,selectedBottomItemState.value)
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
                    .fillMaxWidth()
            ){
                items(favorite) { book ->
                    FavBookListItemUi(book = book, onClick = { onBookClick(book) })
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            Button(
                onClick = {
                    coroutineScope.launch  {
                        favoriteViewModel.clearFavorites()
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonColor
                )
            ) {
                Text(text = "Очистить")
            }
        }
    }
}
