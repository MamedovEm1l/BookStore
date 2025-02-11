package com.example.bookstore.model

data class BasketItem(
    val book: Book = Book(),
    var quantity: Int = 1
)

