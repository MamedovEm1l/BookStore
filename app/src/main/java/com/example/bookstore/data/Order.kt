package com.example.bookstore.data

import java.util.Date

data class Order(
    val id: String,
    val date: Date = Date(),
    val books: List<Book>,
    val totalCost: Double
)
