package com.example.bookstore

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.bookstore.data.Book
import com.example.bookstore.data.Order
import com.example.bookstore.navigation.Screen
import com.example.bookstore.screens.add_book_screen.AddBookScreen
import com.example.bookstore.screens.add_book_screen.data.AddScreenObject
import com.example.bookstore.screens.bookdetails.BookDetailsScreen
import com.example.bookstore.screens.cart.CartScreen
import com.example.bookstore.screens.favorites.FavoritesScreen
import com.example.bookstore.screens.history.OrderHistoryScreen
import com.example.bookstore.screens.login.Data.LoginScreenObject
import com.example.bookstore.screens.login.Data.MainScreenDataObject
import com.example.bookstore.screens.login.LoginScreen
import com.example.bookstore.screens.main_screen.MainScreen
import com.example.bookstore.screens.register.RegisterScreen
import com.example.bookstore.screens.register.data.RegisterScreenObject
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val auth = Firebase.auth

        setContent {
            val navController = rememberNavController()
            val books = listOf(
                Book(key = "12","dgh","hiffg","100", "drama",  "https://firebasestorage.googleapis.com/v0/b/bookstore-d9c83.appspot.com/o/book_images%2Fimage_1731497939863.jpg?alt=media&token=e77e8a72-7456-4d49-a8bb-404697be2092" ),
                Book(key = "12","dgh","hiffg","100", "drama",  "https://firebasestorage.googleapis.com/v0/b/bookstore-d9c83.appspot.com/o/book_images%2Fimage_1731497939863.jpg?alt=media&token=e77e8a72-7456-4d49-a8bb-404697be2092" ),
                Book(key = "12","dgh","hiffg","100", "drama",  "https://firebasestorage.googleapis.com/v0/b/bookstore-d9c83.appspot.com/o/book_images%2Fimage_1731497939863.jpg?alt=media&token=e77e8a72-7456-4d49-a8bb-404697be2092" ),
            )
            NavHost(
                navController = navController,
                //startDestination = Screen.OrderHistory.route
                startDestination = if(auth.currentUser != null) {
                    MainScreenDataObject(auth.currentUser!!.uid, auth.currentUser?.email!!)
                } else {
                    LoginScreenObject
                }
            ){

                composable <LoginScreenObject>{
                    LoginScreen (
                        onNavigateToMainScreen = { navData ->
                            navController.navigate(navData)
                        },
                        onNavigateToRegister = {
                            navController.navigate(RegisterScreenObject)
                        }
                    )
                }

                composable <RegisterScreenObject>{
                    RegisterScreen (
                        onNavigateToMainScreen = { navData ->
                            navController.navigate(navData) {
                                popUpTo(LoginScreenObject) { inclusive = true }
                            }
                        },
                        onNavigateBack = {
                            navController.popBackStack()
                        }
                    )
                }

                composable<MainScreenDataObject>{ navEntry ->
                    val navData = navEntry.toRoute<MainScreenDataObject>()
                    Log.d("MyLog", auth.currentUser?.email!!)
                    MainScreen(
                        navData
                    ){
                        navController.navigate(AddScreenObject)
                    }
                }
//                composable(Screen.Main.route) {
//                    MainScreen(
//                        navData = MainScreenDataObject(),
//                        onAdminClick = {}
//                        onFavoritesClick = { navController.navigate(Screen.Favorites.route) },
//                        onCartClick = { navController.navigate(Screen.Cart.route) },
//                        onOrderHistoryClick = { navController.navigate(Screen.OrderHistory.route) },
//                        onBookClick = { book -> navController.navigate(Screen.BookDetails.createRoute(book.key)) }
//                    )
//                }
                composable(Screen.Favorites.route) {
                    FavoritesScreen(
                        favorites = listOf(
                            Book(key = "12","dgh","hiffg","100", "drama",  "https://firebasestorage.googleapis.com/v0/b/bookstore-d9c83.appspot.com/o/book_images%2Fimage_1731497939863.jpg?alt=media&token=e77e8a72-7456-4d49-a8bb-404697be2092" ),
                            Book(key = "12","dgh","hiffg","100", "drama",  "https://firebasestorage.googleapis.com/v0/b/bookstore-d9c83.appspot.com/o/book_images%2Fimage_1731497939863.jpg?alt=media&token=e77e8a72-7456-4d49-a8bb-404697be2092" ),
                            Book(key = "12","dgh","hiffg","100", "drama",  "https://firebasestorage.googleapis.com/v0/b/bookstore-d9c83.appspot.com/o/book_images%2Fimage_1731497939863.jpg?alt=media&token=e77e8a72-7456-4d49-a8bb-404697be2092" ),
                        ), // Здесь можно передать список избранных книг
                        onBookClick = { book -> {} },
                        onRemoveFromFavorites = { /* Обработка удаления из избранного */ }
                    )
                }
                composable(Screen.Cart.route) {
                    CartScreen(
                        cartItems = listOf(
                            Book(key = "12","dgh","hiffg","100", "drama",  "https://firebasestorage.googleapis.com/v0/b/bookstore-d9c83.appspot.com/o/book_images%2Fimage_1731497939863.jpg?alt=media&token=e77e8a72-7456-4d49-a8bb-404697be2092" ),
                            Book(key = "12","dgh","hiffg","100", "drama",  "https://firebasestorage.googleapis.com/v0/b/bookstore-d9c83.appspot.com/o/book_images%2Fimage_1731497939863.jpg?alt=media&token=e77e8a72-7456-4d49-a8bb-404697be2092" ),
                            Book(key = "12","dgh","hiffg","100", "drama",  "https://firebasestorage.googleapis.com/v0/b/bookstore-d9c83.appspot.com/o/book_images%2Fimage_1731497939863.jpg?alt=media&token=e77e8a72-7456-4d49-a8bb-404697be2092" ),
                        ), // Здесь можно передать список избранных книг
                        onBookClick = { book -> {} },
                        onCheckout = { /* Обработка оформления заказа */ }
                    )
                }
                composable(Screen.OrderHistory.route) {
                    OrderHistoryScreen(
                        orders = listOf(
                            Order("1", books = books, totalCost = 50.4),
                            Order("1", books = books, totalCost = 50.4),
                            Order("1", books = books, totalCost = 50.4),
                            Order("1", books = books, totalCost = 50.4)
                        ), // Здесь можно передать список заказов
                        onOrderClick = { order -> /* Обработка выбора заказа */ }
                    )
                }
                composable(Screen.BookDetails.route) { backStackEntry ->
                    val bookId = backStackEntry.arguments?.getString("bookId")
                    // Получение книги по bookId
                    BookDetailsScreen(book = Book(bookId ?: "", "Название книги", "Автор", "Описание", "0"))
                }
                composable<AddScreenObject>{
                    AddBookScreen{
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}
