package com.example.bookstore.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.bookstore.model.BasketItem
import com.example.bookstore.model.Book
import com.example.bookstore.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RegisterViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _nameState = MutableStateFlow("")
    val nameState: StateFlow<String> = _nameState

    private val _emailState = MutableStateFlow("")
    val emailState: StateFlow<String> = _emailState

    private val _numberState = MutableStateFlow("")
    val numberState: StateFlow<String> = _numberState

    private val _passwordState = MutableStateFlow("")
    val passwordState: StateFlow<String> = _passwordState

    private val _confirmPasswordState = MutableStateFlow("")
    val confirmPasswordState: StateFlow<String> = _confirmPasswordState

    private val _errorState = MutableStateFlow("")
    val errorState: StateFlow<String> = _errorState

    fun onNameChange(newName: String) {
        _nameState.value = newName
    }

    fun onEmailChange(newEmail: String) {
        _emailState.value = newEmail
    }

    fun onNumberChange(newNumber: String) {
        _numberState.value = newNumber
    }

    fun onPasswordChange(newPassword: String) {
        _passwordState.value = newPassword
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        _confirmPasswordState.value = newConfirmPassword
    }

    private fun isNameValid(name: String): Boolean {
        return name.isNotBlank() && name.length >= 2
    }

    private fun isEmailValid(email: String): Boolean {
        return email.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isNumberValid(number: String): Boolean {
        return number.isNotBlank() && number.matches(Regex("\\+?\\d{10,15}"))
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.isNotBlank() && password.length >= 8
    }
    fun onError(message: String) {
        _errorState.value = message
    }

    fun signUp(
        onSignUpSuccess: (User) -> Unit,
        onSignUpFailure: (String) -> Unit
    ) {
        val name = _nameState.value
        val email = _emailState.value
        val password = _passwordState.value
        val address = ""
        val confirmPassword = _confirmPasswordState.value
        val number = _numberState.value

        // Валидация полей
        when {
            !isNameValid(name) -> {
                _errorState.value = "Invalid name. Name must be at least 2 characters."
                return
            }
            !isEmailValid(email) -> {
                _errorState.value = "Invalid email format."
                return
            }
            !isNumberValid(number) -> {
                _errorState.value = "Invalid phone number. Must be 10-15 digits."
                return
            }
            !isPasswordValid(password) -> {
                _errorState.value = "Password must be at least 8 characters."
                return
            }
            password != confirmPassword -> {
                _errorState.value = "Passwords do not match."
                return
            }
        }
        try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val userId = user?.uid ?: ""

                        val userData = mapOf(
                            "uid" to userId,
                            "name" to name,
                            "email" to email,
                            "password" to password,
                            "number" to number,
                            "address" to address,
                            "favorite" to emptyList<Book>(),
                            "basket" to emptyList<BasketItem>()
                        )

                        FirebaseFirestore.getInstance()
                            .collection("users")
                            .document(userId)
                            .set(userData)
                            .addOnSuccessListener {
                                onSignUpSuccess(
                                    User(
                                        uid = userId,
                                        name = name,
                                        email = email,
                                        password = password,
                                        number = number,
                                        address = address,
                                        favorite = emptyList(),
                                        basket = emptyList()
                                    )
                                )
                            }
                            .addOnFailureListener {
                                _errorState.value = it.localizedMessage ?: "Sign Up Error"
                                onSignUpFailure(_errorState.value)
                            }
                    }
                }
                .addOnFailureListener {
                    _errorState.value = it.localizedMessage ?: "Sign Up Error"
                    onSignUpFailure(_errorState.value)
                }
        } catch (e: Exception) {
            Log.e("Registration", "Error during registration: ${e.localizedMessage}")
        }
    }
    fun resetState() {
        _nameState.value = ""
        _emailState.value = ""
        _numberState.value = ""
        _passwordState.value = ""
        _confirmPasswordState.value = ""
        _errorState.value = ""
    }
}

