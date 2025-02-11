package com.example.bookstore.model

data class FilterState(
    val priceRange: IntRange? = null,
    val genre: String? = null,
    val isInStock: Boolean? = null
)