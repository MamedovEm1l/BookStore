package com.example.bookstore.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstore.model.Book
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AddBookViewModel(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow("Drama")
    val selectedCategory: StateFlow<String> = _selectedCategory

    private val _selectedImageUri = MutableStateFlow<Uri?>(null)
    val selectedImageUri: StateFlow<Uri?> = _selectedImageUri

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private val _author = MutableStateFlow("")
    val author: StateFlow<String> = _author

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description

    private val _publicationDate = MutableStateFlow("")
    val publicationDate: StateFlow<String> = _publicationDate

    private val _publisher = MutableStateFlow("")
    val publisher: StateFlow<String> = _publisher

    private val _quantity = MutableStateFlow("0")
    val quantity: StateFlow<String> = _quantity

    private val _price = MutableStateFlow("0")
    val price: StateFlow<String> = _price

    private val _isStock = MutableStateFlow("true")
    val isStock: StateFlow<String> = _isStock

    fun loadBookData(bookId: String) {
        viewModelScope.launch {
            val snapshot = firestore.collection("books").document(bookId).get().await()
            val book = snapshot.toObject(Book::class.java)
            if (book != null) {
                _title.value = book.title
                _author.value = book.author
                _description.value = book.description
                _publicationDate.value = book.publicationDate
                _publisher.value = book.publisher
                _quantity.value = book.quantity.toString()
                _price.value = book.price.dropLast(2) // Убираем " ₽"
                _isStock.value = book.isStock.toString()
                _selectedCategory.value = book.category
                _selectedImageUri.value = Uri.parse(book.imageUrl)
            }
        }
    }

    fun updateBook(
        bookId: String,
        onUpdated: () -> Unit,
        onError: (String) -> Unit
    ) {
        val imageUri = _selectedImageUri.value
        if (imageUri == null) {
            onError("Image not selected")
            return
        }

        viewModelScope.launch {
            try {
                val timeStamp = System.currentTimeMillis()
                val storageRef = storage.reference
                    .child("book_images/image_$timeStamp.jpg")

                // Проверяем, локальный ли это URI
                if (imageUri.scheme == "content" || imageUri.scheme == "file") {
                    val uploadTask = storageRef.putFile(imageUri)
                    uploadTask.addOnSuccessListener {
                        storageRef.downloadUrl.addOnSuccessListener { url ->
                            val book = Book(
                                title = _title.value,
                                author = _author.value,
                                description = _description.value,
                                price = _price.value + " ₽",
                                category = _selectedCategory.value,
                                publicationDate = _publicationDate.value,
                                isStock = _isStock.value.toBoolean(),
                                quantity = _quantity.value.toInt(),
                                publisher = _publisher.value,
                                key = bookId,
                                imageUrl = url.toString()
                            )
                            firestore.collection("books").document(bookId)
                                .set(book)
                                .addOnSuccessListener { onUpdated() }
                                .addOnFailureListener { onError(it.message ?: "Failed to update book") }
                        }
                    }.addOnFailureListener {
                        onError(it.message ?: "Failed to upload image")
                    }
                } else {
                    // Если URI не локальный, используем его напрямую
                    val book = Book(
                        title = _title.value,
                        author = _author.value,
                        description = _description.value,
                        price = _price.value + " ₽",
                        category = _selectedCategory.value,
                        publicationDate = _publicationDate.value,
                        isStock = _isStock.value.toBoolean(),
                        quantity = _quantity.value.toInt(),
                        publisher = _publisher.value,
                        key = bookId,
                        imageUrl = imageUri.toString()
                    )
                    firestore.collection("books").document(bookId)
                        .set(book)
                        .addOnSuccessListener { onUpdated() }
                        .addOnFailureListener { onError(it.message ?: "Failed to update book") }
                }
            } catch (e: Exception) {
                onError(e.message ?: "Unexpected error")
            }
        }
    }

    fun updateCategory(category: String) {
        _selectedCategory.value = category
    }

    fun updateField(field: String, value: String) {
        when (field) {
            "title" -> _title.value = value
            "author" -> _author.value = value
            "description" -> _description.value = value
            "publicationDate" -> _publicationDate.value = value
            "publisher" -> _publisher.value = value
            "quantity" -> _quantity.value = value
            "price" -> _price.value = value
        }
    }

    fun updateIsStock(stockStatus: String) {
        _isStock.value = stockStatus
    }

    fun setImageUri(uri: Uri) {
        _selectedImageUri.value = uri
    }

    fun saveBook(
        onSaved: () -> Unit,
        onError: (String) -> Unit
    ) {
        val imageUri = _selectedImageUri.value
        if (imageUri == null) {
            onError("Image not selected")
            return
        }

        viewModelScope.launch {
            try {
                val timeStamp = System.currentTimeMillis()
                val storageRef = storage.reference
                    .child("book_images/image_$timeStamp.jpg")
                val uploadTask = storageRef.putFile(imageUri)
                uploadTask.addOnSuccessListener {
                    storageRef.downloadUrl.addOnSuccessListener { url ->
                        val book = Book(
                            title = _title.value,
                            author = _author.value,
                            description = _description.value,
                            price = _price.value + " ₽",
                            category = _selectedCategory.value,
                            publicationDate = _publicationDate.value,
                            isStock = _isStock.value.toBoolean(),
                            quantity = _quantity.value.toInt(),
                            publisher = _publisher.value,
                            key = "",
                            imageUrl = url.toString()
                        )
                        saveBookToFireStore(book, onSaved, onError)
                    }
                }.addOnFailureListener {
                    onError(it.message ?: "Image upload failed")
                }
            } catch (e: Exception) {
                onError(e.message ?: "An error occurred")
            }
        }
    }

    private fun saveBookToFireStore(
        book: Book,
        onSaved: () -> Unit,
        onError: (String) -> Unit
    ) {
        val db = firestore.collection("books")
        val key = db.document().id
        db.document(key)
            .set(book.copy(key = key))
            .addOnSuccessListener {
                Log.i("AddBookViewModel", "Book saved successfully")
                onSaved()
            }
            .addOnFailureListener {
                Log.e("AddBookViewModel", "Failed to save book: ${it.message}")
                onError(it.message ?: "Failed to save book")
            }
    }
}
