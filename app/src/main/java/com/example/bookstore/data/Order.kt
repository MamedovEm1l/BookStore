package com.example.bookstore.data

import java.util.Date

data class Order(
    val id: String,
    val date: Date = Date(),
    val books: List<Book>,
    val totalCost: Double,
    val discountPrice: Double = 100.0,   // Добавлено для отображения исходной цены
    val timeAgo: String = "7 минут"
)
