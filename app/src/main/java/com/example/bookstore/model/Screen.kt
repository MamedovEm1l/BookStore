package com.example.bookstore.model

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Main : Screen("main")
    data object AddBook : Screen("add_book")
    data object Favorites : Screen("favorites")
    data object OrderHistory : Screen("order_history")
    data object Profile : Screen("profile")
    data object Basket : Screen("basket")
    data object OrderForm :  Screen("order_form")
    data object BookDetails : Screen("book_details/{bookId}") {
        fun createRoute(bookId: String) = "book_details/$bookId"
    }
}
