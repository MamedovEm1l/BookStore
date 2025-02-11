package com.example.bookstore.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.bookstore.model.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

class OrderViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders

    suspend fun saveOrder(order: Order): Order {
        val currentUser = firebaseAuth.currentUser ?: throw Exception("Пользователь не авторизован.")

        try {
            val documentRef = firestore.collection("users")
                .document(currentUser.uid)
                .collection("orders")
                .add(order)
                .await()

            val generatedId = documentRef.id

            firestore.collection("users")
                .document(currentUser.uid)
                .collection("orders")
                .document(generatedId)
                .update("id", generatedId)
                .await()

            Log.i("OrderViewModel", "Order successfully saved with id: $generatedId")
            return order.copy(id = generatedId)
        } catch (e: Exception) {
            Log.e("OrderViewModel", "Error saving order: ${e.localizedMessage}")
            throw e
        }
    }



    suspend fun fetchOrders() {
        val currentUser = firebaseAuth.currentUser
        if (currentUser == null) {
            Log.e("OrderViewModel", "Пользователь не авторизован.")
            return
        }
        try {
            val snapshot = firestore.collection("users")
                .document(currentUser.uid)
                .collection("orders")
                .get()
                .await()

            val ordersList = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Order::class.java)
            }

            _orders.value = ordersList
        } catch (e: Exception) {
            Log.e("OrderViewModel", "Error fetching orders: ${e.localizedMessage}")
        }
    }
}
