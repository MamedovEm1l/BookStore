package com.example.bookstore.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstore.model.Book
import com.example.bookstore.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FavoriteViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _favorite = MutableStateFlow<List<Book>>(emptyList())
    val favorite: StateFlow<List<Book>> = _favorite


    fun addBook(book: Book) {
        _favorite.update { currentFavorites ->
            if (currentFavorites.none { it.key == book.key }) {
                currentFavorites + book
            } else {
                currentFavorites
            }
        }
        saveFavorites()
    }


    fun removeBook(book: Book) {
        _favorite.update { currentFavorites -> currentFavorites - book }
    }

    suspend fun clearFavorites() {
        val userId = auth.currentUser?.uid ?: return
        try {
            // Удаляем данные из Firestore
            val userDoc = firestore.collection("users").document(userId)
            userDoc.update("favorite", emptyList<Book>()).await()

            // Очищаем локальное состояние
            _favorite.value = emptyList()

            Log.i("FavoriteViewModel", "Favorites cleared successfully.")
        } catch (e: Exception) {
            Log.e("FavoriteViewModel", "Error clearing favorites: ${e.message}", e)
        }
    }

    /**
     * Сохранить список избранного в Firestore
     */
     fun saveFavorites() {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: return@launch
            try {
                val userDoc = firestore.collection("users").document(userId)

                // Обновление поля favorite в документе пользователя
                userDoc.update("favorite", _favorite.value).await()
                Log.i("FavoriteViewModel", "Favorites saved successfully.")
            } catch (e: Exception) {
                Log.e("FavoriteViewModel", "Error saving favorites: ${e.message}", e)
            }
        }
    }

    /**
     * Загрузить список избранного из Firestore
     */
    suspend fun fetchFavorites() {
        val userId = auth.currentUser?.uid ?: return
        try {
            val snapshot = firestore.collection("users").document(userId).get().await()

            // Получаем список избранного из документа пользователя
            val favoritesList = snapshot.toObject(User::class.java)?.favorite ?: emptyList()

            // Обновляем локальное состояние
            _favorite.value = favoritesList

            Log.i("FavoriteViewModel", "Favorites fetched successfully.")
        } catch (e: Exception) {
            Log.e("FavoriteViewModel", "Error fetching favorites: ${e.message}", e)
        }
    }
}
