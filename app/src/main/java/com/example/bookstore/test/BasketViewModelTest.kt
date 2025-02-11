package com.example.bookstore.test
import com.example.bookstore.model.Book
import com.example.bookstore.viewmodel.BasketViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BasketViewModelTest {

    private lateinit var basketViewModel: BasketViewModel

    @Before
    fun setup() {
        basketViewModel = BasketViewModel()
    }

    @Test
    fun addBook() {
        // Arrange
        val book = Book(key = "1", title = "Test Book", price = "100", isStock = true)

        // Act
        basketViewModel.addBook(book)

        // Assert
        val basket = basketViewModel.basket.value
        assertEquals(1, basket.size)
        assertEquals("Test Book", basket[0].book.title)
    }
}
