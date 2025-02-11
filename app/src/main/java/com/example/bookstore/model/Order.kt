package com.example.bookstore.model

data class Order(
    val address: String = "",
    val date: String = "",
    val time: String = "",
    val totalPrice:Double = 0.0,
    val items: List<BasketItem> = emptyList(),
    val state: Boolean = true,
    val id: String = "",
    val refund: List<BasketItem> = emptyList()
)
