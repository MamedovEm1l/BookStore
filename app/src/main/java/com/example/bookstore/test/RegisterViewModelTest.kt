package com.example.bookstore.test

import com.example.bookstore.viewmodel.RegisterViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RegisterViewModelTest {

    private lateinit var registerViewModel: RegisterViewModel

    @Before
    fun setup() {
        registerViewModel = RegisterViewModel()
    }

    @Test
    fun signUp() = run {
        // Arrange
        registerViewModel.onEmailChange("mamedov@gmail.com")
        registerViewModel.onPasswordChange("password")
        registerViewModel.onConfirmPasswordChange("password")

        // Act
        registerViewModel.signUp(
            onSignUpSuccess = { },
            onSignUpFailure = { message ->
                // Assert
                assertEquals("Password must be at least 8 characters.", message)
            }
        )
    }
}
