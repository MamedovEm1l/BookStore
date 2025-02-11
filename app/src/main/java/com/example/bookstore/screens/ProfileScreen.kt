package com.example.bookstore.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bookstore.R
import com.example.bookstore.composables.BottomMenu
import com.example.bookstore.composables.RoundedCornerTextField
import com.example.bookstore.model.Screen
import com.example.bookstore.screens.main_screen.bottom_menu.BottomMenuItem
import com.example.bookstore.ui.theme.ButtonColor
import com.example.bookstore.ui.theme.CreamColor
import com.example.bookstore.viewmodel.LoginViewModel
import com.example.bookstore.viewmodel.MainViewModel
import com.example.bookstore.viewmodel.ProfileViewModel
import com.example.bookstore.viewmodel.RegisterViewModel

@Composable
fun ProfileScreen(
    mainViewModel: MainViewModel = viewModel(),
    viewModel: ProfileViewModel = viewModel(),
    navController: NavController
) {
    val name by viewModel.name.collectAsState()
    val email by viewModel.email.collectAsState()
    val number by viewModel.number.collectAsState()
    val password by viewModel.password.collectAsState()
    val statusMessage by viewModel.statusMessage.collectAsState()
    val loginViewModel: LoginViewModel = viewModel()
    val registerViewModel: RegisterViewModel = viewModel()

    val selectedBottomItemState = remember {
        mutableStateOf(BottomMenuItem.Profile.title)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamColor),
        bottomBar = {
            BottomMenu(navController = navController, selectedBottomItemState.value)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CreamColor)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Заголовок экрана
            Text(
                text = "Profile",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                fontFamily = FontFamily.Serif
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Аватар
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(R.drawable.profile),
                contentDescription = "Profile Image"
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Имя пользователя
            RoundedCornerTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                label = "Name",
                text = name,
                onValueChange = { viewModel.updateField("name", it) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Email
            RoundedCornerTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                label = "Email",
                text = email,
                onValueChange = { viewModel.updateField("email", it) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Номер телефона
            RoundedCornerTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                label = "Number",
                text = number,
                onValueChange = { viewModel.updateField("number", it) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Пароль
            RoundedCornerTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                label = "Password",
                text = password,
                onValueChange = { viewModel.updateField("password", it) }
            )

            Spacer(modifier = Modifier.weight(1f))

            // Сообщение о статусе
            if (statusMessage.isNotEmpty()) {
                Text(
                    text = statusMessage,
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Button(
                onClick = { viewModel.saveUserProfile() },
                modifier = Modifier
                    .fillMaxWidth(0.7f),
                colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
            ) {
                Text(
                    text = "Save",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { mainViewModel.logout(
                    loginViewModel = loginViewModel,
                    registerViewModel = registerViewModel
                ) {
                    navController.navigate(Screen.Login.route)
                } },
                modifier = Modifier
                    .fillMaxWidth(0.5f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text(
                    text = "Exit",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
