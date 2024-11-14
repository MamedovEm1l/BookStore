package com.example.bookstore.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bookstore.data.Book
import com.example.bookstore.data.Order
import com.example.bookstore.screens.bookdetails.BookDetailsScreen
import com.example.bookstore.screens.cart.CartScreen
import com.example.bookstore.screens.favorites.FavoritesScreen
import com.example.bookstore.screens.history.OrderHistoryScreen
import com.example.bookstore.screens.login.Data.MainScreenDataObject
import com.example.bookstore.screens.main_screen.MainScreen
import com.google.type.Date
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Favorites : Screen("favorites")
    object Cart : Screen("cart")
    object OrderHistory : Screen("order_history")
    object BookDetails : Screen("book_details/{bookId}") {
        fun createRoute(bookId: String) = "book_details/$bookId"
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onBookClick: (Book) -> Unit,
    onOrderClick: (Order) -> Unit
) {
    val books = listOf(
        Book(key = "12","dgh","hiffg","100", "drama",  "https://firebasestorage.googleapis.com/v0/b/bookstore-d9c83.appspot.com/o/book_images%2Fimage_1731497939863.jpg?alt=media&token=e77e8a72-7456-4d49-a8bb-404697be2092" ),
        Book(key = "12","dgh","hiffg","100", "drama",  "https://firebasestorage.googleapis.com/v0/b/bookstore-d9c83.appspot.com/o/book_images%2Fimage_1731497939863.jpg?alt=media&token=e77e8a72-7456-4d49-a8bb-404697be2092" ),
        Book(key = "12","dgh","hiffg","100", "drama",  "https://firebasestorage.googleapis.com/v0/b/bookstore-d9c83.appspot.com/o/book_images%2Fimage_1731497939863.jpg?alt=media&token=e77e8a72-7456-4d49-a8bb-404697be2092" ),
        Book(key = "12","dgh","hiffg","100", "drama",  "https://firebasestorage.googleapis.com/v0/b/bookstore-d9c83.appspot.com/o/book_images%2Fimage_1731497939863.jpg?alt=media&token=e77e8a72-7456-4d49-a8bb-404697be2092" )
    )
        NavHost(
        navController = navController,
        startDestination = Screen.Main.route,
        modifier = modifier
    ) {
        composable(Screen.Main.route) {
            MainScreen(
                navData = MainScreenDataObject(),
                onAdminClick = {}
//                onFavoritesClick = { navController.navigate(Screen.Favorites.route) },
//                onCartClick = { navController.navigate(Screen.Cart.route) },
//                onOrderHistoryClick = { navController.navigate(Screen.OrderHistory.route) },
//                onBookClick = { book -> navController.navigate(Screen.BookDetails.createRoute(book.key)) }
            )
        }
        composable(Screen.Favorites.route) {
            FavoritesScreen(
                favorites = books,// Здесь можно передать список избранных книг
                onBookClick = { book -> {} },
                onRemoveFromFavorites = { /* Обработка удаления из избранного */ }
            )
        }
        composable(Screen.Cart.route) {
            CartScreen(
                cartItems = books,
                onBookClick = { book -> {}},
                onCheckout = { /* Обработка оформления заказа */ }
            )
        }
        composable(Screen.OrderHistory.route) {
            OrderHistoryScreen(
                orders = listOf(Order("1", books = books, totalCost = 50.4),Order("1", books = books, totalCost = 50.4),Order("1", books = books, totalCost = 50.4),Order("1", books = books, totalCost = 50.4)), // Здесь можно передать список заказов
                onOrderClick = { order -> { } }
            )
        }
        composable(Screen.BookDetails.route) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId")
            // Получение книги по bookId
            BookDetailsScreen(book = Book(bookId ?: "", "Название книги", "Автор", "Описание", "0"))
        }
    }
}
