package com.example.bookstore.test

import com.example.bookstore.model.Book
import com.example.bookstore.viewmodel.MainViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setup() {
        mainViewModel = MainViewModel()
    }

    @Test
    fun applyFilter()  {
        // Arrange
        val book1 = Book(key = "1", title = "Book A", price = "200", category = "Fiction", isStock = true)
        val book2 = Book(key = "2", title = "Book B", price = "300", category = "Non-Fiction", isStock = true)

        mainViewModel.loadBooks(listOf(book1, book2))

        // Act
        mainViewModel.applyFilter(priceRange = 100..250, genre = "Fiction", isInStock = true)

        // Assert
        val filteredBooks = mainViewModel.bookList.value
        assertEquals(1, filteredBooks.size)
        assertEquals("Book A", filteredBooks[0].title)
    }
}
