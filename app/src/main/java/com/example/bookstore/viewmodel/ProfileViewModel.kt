package com.example.bookstore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // Текущие значения полей
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> get() = _name

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email

    private val _number = MutableStateFlow("")
    val number: StateFlow<String> get() = _number

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    // Оригинальные значения полей для сравнения
    private val originalValues = mutableMapOf<String, String>()

    private val _statusMessage = MutableStateFlow("")
    val statusMessage: StateFlow<String> get() = _statusMessage

    init {
        viewModelScope.launch {
            if (auth.currentUser != null) {
                loadUserProfile()
            }
        }
    }

    private fun loadUserProfile() {
        val userId = auth.currentUser!!.uid
        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    _name.value = document.getString("name") ?: ""
                    _email.value = document.getString("email") ?: ""
                    _number.value = document.getString("number") ?: ""
                    _password.value = document.getString("password") ?: ""

                    // Сохраняем оригинальные значения
                    originalValues["name"] = _name.value
                    originalValues["email"] = _email.value
                    originalValues["number"] = _number.value
                    originalValues["password"] = _password.value
                }
            }
            .addOnFailureListener {
                _statusMessage.value = "Failed to load profile: ${it.message}"
            }
    }

    fun updateField(field: String, value: String) {
        when (field) {
            "name" -> _name.value = value
            "email" -> _email.value = value
            "number" -> _number.value = value
            "password" -> _password.value = value
        }
    }

    fun saveUserProfile() {
        val userId = auth.currentUser?.uid ?: return

        // Собираем только изменённые поля
        val updatedFields = mutableMapOf<String, String>()
        if (_name.value != originalValues["name"]) updatedFields["name"] = _name.value
        if (_email.value != originalValues["email"]) updatedFields["email"] = _email.value
        if (_number.value != originalValues["number"]) updatedFields["number"] = _number.value
        if (_password.value != originalValues["password"]) updatedFields["password"] = _password.value

        // Если изменений нет, ничего не сохраняем
        if (updatedFields.isEmpty()) {
            _statusMessage.value = "No changes to save."
            return
        }

        // Обновляем только изменённые поля в Firestore
        viewModelScope.launch {
            db.collection("users").document(userId).update(updatedFields as Map<String, Any>)
                .addOnSuccessListener {
                    // Обновляем оригинальные значения
                    updatedFields.forEach { (key, value) ->
                        originalValues[key] = value
                    }
                    _statusMessage.value = "Profile updated successfully"
                }
                .addOnFailureListener {
                    _statusMessage.value = "Failed to update profile: ${it.message}"
                }
        }
    }
}