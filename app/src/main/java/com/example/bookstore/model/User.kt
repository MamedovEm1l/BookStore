package com.example.bookstore.model

data class User(
    var uid:String = "",
    var name:String = "",
    var email:String = "",
    var password:String = "",
    var number:String = "",
    var address:String = "",
    val favorite: List<Book> = emptyList(),
    val basket: List<BasketItem> = emptyList()
)
