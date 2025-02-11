package com.example.bookstore.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bookstore.model.MainScreenDataObject
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _emailState = MutableStateFlow("mamedovemil366@gmail.com")
    val emailState: StateFlow<String> = _emailState

    private val _passwordState = MutableStateFlow("Movs2005")
    val passwordState: StateFlow<String> = _passwordState

    private val _errorState = MutableStateFlow("")
    val errorState: StateFlow<String> = _errorState

    fun onEmailChange(newEmail: String) {
        _emailState.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _passwordState.value = newPassword
    }

    fun onError(message: String) {
        _errorState.value = message
    }

    fun signIn(
        onSignInSuccess: (MainScreenDataObject) -> Unit,
        onSignInFailure: (String) -> Unit
    ) {
        val email = _emailState.value
        val password = _passwordState.value

        if (email.isBlank() || password.isBlank()) {
            _errorState.value = "Email and password cannot be empty"
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = task.result.user
                    onSignInSuccess(MainScreenDataObject(user?.uid ?: "", user?.email ?: ""))
                } else {
                    _errorState.value = "Sign In Error"
                }
            }
            .addOnFailureListener {
                _errorState.value = it.localizedMessage ?: "Sign In Error"
                onSignInFailure(_errorState.value)
            }
    }
    fun resetState() {
        _emailState.value = ""
        _passwordState.value = ""
        _errorState.value = ""
    }
}
