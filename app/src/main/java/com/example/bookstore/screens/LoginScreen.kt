package com.example.bookstore.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookstore.R
import com.example.bookstore.composables.LoginButton
import com.example.bookstore.composables.RoundedCornerTextField
import com.example.bookstore.model.MainScreenDataObject
import com.example.bookstore.ui.theme.BoxFilterColor
import com.example.bookstore.ui.theme.ButtonColor
import com.example.bookstore.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel(),
    onNavigateToMainScreen: (MainScreenDataObject) -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val email by loginViewModel.emailState.collectAsState()
    val password by loginViewModel.passwordState.collectAsState()
    val error by loginViewModel.errorState.collectAsState()

    Image(painter = painterResource(
        id = R.drawable.book_store_bg),
        contentDescription = "BG",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BoxFilterColor)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 50.dp,
                end = 50.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(
            id = R.drawable.books),
            contentDescription = "Logo"
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "MOV Book Store",
            color = ButtonColor,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            fontFamily = FontFamily.Serif
        )
        Spacer (modifier = Modifier.height (15.dp))
        RoundedCornerTextField(
            text = email,
            onValueChange = loginViewModel::onEmailChange,
            label = "Email"
        )
        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextField(
            text = password,
            onValueChange = loginViewModel::onPasswordChange,
            visualTransformation = PasswordVisualTransformation(),
            label = "Password"
        )
        Spacer(modifier = Modifier.height(10.dp))
        if(error.isNotEmpty()){
            Text(
                text = error,
                color = Color.Red,
                textAlign = TextAlign.Center
            )
        }
        LoginButton(text = "Sign In") {
            loginViewModel.signIn(
                onSignInSuccess = onNavigateToMainScreen,
                onSignInFailure = { errorMessage ->
                    loginViewModel.onError(errorMessage)
                }
            )
        }
        LoginButton(text = "Sign Up") {
            onNavigateToRegister()
        }
    }
}