package com.example.bookstore.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstore.model.BasketItem
import com.example.bookstore.model.Book
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.bookstore.model.User
import kotlinx.coroutines.tasks.await

class BasketViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _basket = MutableStateFlow<List<BasketItem>>(emptyList())
    val basket: StateFlow<List<BasketItem>> = _basket

    /**
     * Добавить книгу в корзину
     */
    fun addBook(book: Book) {
        _basket.update { currentBasket ->
            val updatedBasket = currentBasket.toMutableList()
            val existingItem = updatedBasket.find { it.book.key == book.key }
            if (existingItem != null) {
                updatedBasket[updatedBasket.indexOf(existingItem)] =
                    existingItem.copy(quantity = existingItem.quantity + 1)
            } else {
                updatedBasket.add(BasketItem(book, quantity = 1))
            }
            updatedBasket
        }
        saveBasket()
    }

    /**
     * Удалить книгу из корзины
     */
    fun removeBook(book: Book) {
        _basket.update { currentBasket ->
            currentBasket.filterNot { it.book.key == book.key }
        }
        saveBasket()
    }

    /**
     * Увеличить количество книги
     */
    fun increaseQuantity(bookKey: String) {
        _basket.update { currentBasket ->
            currentBasket.map {
                if (it.book.key == bookKey) it.copy(quantity = it.quantity + 1) else it
            }
        }
        saveBasket()
    }

    /**
     * Уменьшить количество книги
     */
    fun decreaseQuantity(bookKey: String) {
        _basket.update { currentBasket ->
            currentBasket.mapNotNull {
                if (it.book.key == bookKey) {
                    if (it.quantity > 1) it.copy(quantity = it.quantity - 1) else null
                } else it
            }
        }
        saveBasket()
    }

    /**
     * Очистить корзину
     */
    suspend fun clearBasket() {
        val userId = auth.currentUser?.uid ?: return
        try {
            // Удаляем данные из Firestore
            val userDoc = firestore.collection("users").document(userId)
            userDoc.update("basket", emptyList<BasketItem>()).await()

            // Очищаем локальное состояние
            _basket.value = emptyList()

            Log.i("FavoriteViewModel", "Favorites cleared successfully.")
        } catch (e: Exception) {
            Log.e("FavoriteViewModel", "Error clearing favorites: ${e.message}", e)
        }
    }


    fun saveBasket() {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: return@launch
            try {
                firestore.collection("users")
                    .document(userId)
                    .update("basket", _basket.value) // Сохраняем список BasketItem
                    .await()
                Log.i("BasketViewModel", "Basket saved successfully.")
            } catch (e: Exception) {
                Log.e("BasketViewModel", "Error saving basket: ${e.message}", e)
            }
        }
    }

    fun fetchBasket() {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: return@launch
            try {
                val snapshot = firestore.collection("users")
                    .document(userId)
                    .get()
                    .await()

                // Читаем объект User
                val user = snapshot.toObject(User::class.java)

                // Если поле basket не пустое, обновляем StateFlow
                _basket.value = user?.basket ?: emptyList()

                Log.i("BasketViewModel", "Basket fetched successfully.")
            } catch (e: Exception) {
                Log.e("BasketViewModel", "Error fetching basket: ${e.message}", e)
            }
        }
    }

}
