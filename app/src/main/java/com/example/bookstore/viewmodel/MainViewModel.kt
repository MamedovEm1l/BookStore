package com.example.bookstore.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstore.model.Book
import com.example.bookstore.model.FilterState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainViewModel : ViewModel() {

    private val _bookList = MutableStateFlow<List<Book>>(emptyList())
    val bookList: StateFlow<List<Book>> = _bookList

    private val _allBooks = MutableStateFlow<List<Book>>(emptyList())
    val allBooks: StateFlow<List<Book>> = _allBooks

    private val _isAdminState = MutableStateFlow(false)
    val isAdminState: StateFlow<Boolean> = _isAdminState

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        viewModelScope.launch {
            if (auth.currentUser != null) {
                loadBooks()
                checkAdminStatus(auth.currentUser!!.uid)
            }
        }
    }

    private val _filterState = MutableStateFlow(FilterState())
    val filterState: StateFlow<FilterState> = _filterState

    fun searchBooks(query: String) {
        _bookList.value = if (query.isBlank()) {
            _allBooks.value
        } else {
            _allBooks.value.filter { book ->
                book.title.contains(query, ignoreCase = true)
            }
        }
    }


    fun applyFilter(priceRange: IntRange?, genre: String?, isInStock: Boolean?) {
        _filterState.value = FilterState(priceRange, genre, isInStock)

        _bookList.value = _allBooks.value.filter { book ->
            val matchesPrice = priceRange?.let { book.price.dropLast(2).toIntOrNull() in it } ?: true
            val matchesGenre = if (genre == "All" || genre == null) true else book.category.equals(genre, ignoreCase = true)
            val matchesStock = isInStock?.let { book.isStock == it } ?: true
            matchesPrice && matchesGenre && matchesStock
        }
    }

    fun loadBooks() {
        viewModelScope.launch {
            val snapshot = firestore.collection("books").get().await()
            val books = snapshot.toObjects(Book::class.java)
            _allBooks.value = books
            _bookList.value = books
        }
    }
    fun loadBooks(books: List<Book>) {
        viewModelScope.launch {
           // val snapshot = firestore.collection("books").get().await()
           // val books = snapshot.toObjects(Book::class.java)
            _allBooks.value = books
            _bookList.value = books
        }
    }

    private fun checkAdminStatus(userId: String) {
        firestore.collection("admin")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                _isAdminState.value = document.get("isAdmin") as Boolean
            }
            .addOnFailureListener {
                _isAdminState.value = false
            }
        Log.i("isAdmin?", isAdminState.value.toString())
    }
    fun deleteBook(book: Book) {
        viewModelScope.launch {
            try {
                firestore.collection("books")
                    .document(book.key)
                    .delete()
                    .await()
                loadBooks() // Перезагружаем список книг после удаления
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error deleting book: ${e.message}")
            }
        }
    }

    fun logout(
        loginViewModel: LoginViewModel,
        registerViewModel: RegisterViewModel,
        onLogout: () -> Unit
    ) {
        auth.signOut()
        loginViewModel.resetState()
        registerViewModel.resetState()
        clearData()
        onLogout()
    }
    private fun clearData() {
        _bookList.value = emptyList()
        _allBooks.value = emptyList()
        _isAdminState.value = false
        _filterState.value = FilterState()
    }

}
