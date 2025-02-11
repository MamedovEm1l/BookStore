package com.example.bookstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bookstore.model.Screen
import com.example.bookstore.screens.AddBookScreen
import com.example.bookstore.screens.BasketScreen
import com.example.bookstore.screens.BookDetailsScreen
import com.example.bookstore.screens.FavoritesScreen
import com.example.bookstore.screens.LoginScreen
import com.example.bookstore.screens.OrderFormScreen
import com.example.bookstore.screens.OrderHistoryScreen
import com.example.bookstore.screens.ProfileScreen
import com.example.bookstore.screens.RegisterScreen
import com.example.bookstore.screens.main_screen.MainScreen
import com.example.bookstore.viewmodel.BasketViewModel
import com.example.bookstore.viewmodel.FavoriteViewModel
import com.example.bookstore.viewmodel.LoginViewModel
import com.example.bookstore.viewmodel.OrderViewModel
import com.example.bookstore.viewmodel.ProfileViewModel
import com.example.bookstore.viewmodel.RegisterViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val basketViewModel = viewModel<BasketViewModel>()
            val favoriteViewModel = viewModel<FavoriteViewModel>()
            val orderViewModel = viewModel<OrderViewModel>()
            val loginViewModel = viewModel<LoginViewModel>()
            val registerViewModel = viewModel<RegisterViewModel>()
            val profileViewModel = viewModel<ProfileViewModel>()

            val startDestination = if (Firebase.auth.currentUser != null) {
                Screen.Main.route
            } else {
                "auth"
            }

            NavHost(navController = navController, startDestination = startDestination) {
                navigation(startDestination = Screen.Login.route, route = "auth") {
                    composable(Screen.Login.route) {
                        LoginScreen(
                            loginViewModel = loginViewModel,
                            onNavigateToMainScreen = {
                                navController.navigate(Screen.Main.route) {
                                    popUpTo("auth") { inclusive = true }
                                }
                            },
                            onNavigateToRegister = {
                                navController.navigate(Screen.Register.route)
                            }
                        )
                    }
                    composable(Screen.Register.route) {
                        RegisterScreen(
                            registerViewModel = registerViewModel,
                            onNavigateToMainScreen = {
                                navController.navigate(Screen.Main.route) {
                                    popUpTo("auth") { inclusive = true }
                                }
                            },
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                }

                composable(Screen.Main.route) {
                    MainScreen(
                        navController = navController,
                        onBookEditClick = { book ->
                            navController.navigate("${Screen.AddBook.route}?bookId=${book.key}")
                        },
                        onAdminClick = { navController.navigate(Screen.AddBook.route) }
                    )
                }
                composable("${Screen.AddBook.route}?bookId={bookId}", arguments = listOf(
                    navArgument("bookId") { type = NavType.StringType; nullable = true }
                )) { backStackEntry ->
                    val bookId = backStackEntry.arguments?.getString("bookId")
                    AddBookScreen(bookId = bookId, onSaved = { navController.popBackStack() })
                }

                composable(Screen.Favorites.route) {
                    FavoritesScreen(
                        favoriteViewModel = favoriteViewModel,
                        onBookClick = { book -> navController.navigate(Screen.BookDetails.createRoute(book.key)) },
                        navController = navController
                    )
                }
                composable(Screen.OrderHistory.route) {
                    OrderHistoryScreen(
                        onOrderClick = {   /* Логика выбора заказа */ },
                        orderViewModel = orderViewModel,
                        navController = navController
                    )
                }

                composable(Screen.OrderForm.route) {
                    OrderFormScreen(
                        basketViewModel = basketViewModel,
                        orderViewModel = orderViewModel
                    ){
                        navController.navigate(Screen.Main.route)
                    }
                }

                composable(Screen.Profile.route) {
                    ProfileScreen(  navController = navController)
                }

                composable(Screen.BookDetails.route) { backStackEntry ->
                    val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
                    BookDetailsScreen(
                        bookId = bookId,
                        basketViewModel = basketViewModel,
                        favoriteViewModel = favoriteViewModel
                    )
                }

                composable(Screen.Basket.route) {
                    BasketScreen(
                        basketViewModel = basketViewModel,
                        navController = navController
                    )
                }

            }
        }
    }
}
