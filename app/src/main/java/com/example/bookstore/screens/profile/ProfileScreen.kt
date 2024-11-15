package com.example.bookstore.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bookstore.R
import com.example.bookstore.screens.login.Composables.RoundedCornerTextField
import com.example.bookstore.screens.main_screen.Composables.BottomMenu
import com.example.bookstore.ui.theme.ButtonColor
import com.example.bookstore.ui.theme.CreamColor

@Preview(showBackground = true)
@Composable
fun ProfileScreen(
    navController: NavController = NavController(context = TODO())
) {
    val emailState = remember {
        mutableStateOf("mamedovemil366@gmail.com")
    }
    val passwordState = remember {
        mutableStateOf("Movs2005")
    }
    val nameState = remember {
        mutableStateOf("Emmanuel Oyiboke")
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamColor),
        bottomBar = {
            BottomMenu(navController = navController)
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
                painter = painterResource(R.drawable.profile), // замените на ваш ресурс изображения
                contentDescription = "Profile Image"
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Имя пользователя
            Text(
                text = nameState.value,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Center
            )

            // Ссылка для изменения фото профиля
            Text(
                text = "Change profile foto",
                color = Color.Blue,
                fontSize = 14.sp,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))
            RoundedCornerTextField(
                label = "Name",
                text = nameState.value
            ) {
                nameState.value = it
            }
            Spacer(modifier = Modifier.height(20.dp))
            // Поле Email
            RoundedCornerTextField(label = "Email", text = emailState.value) {
                emailState.value = it
            }
            Spacer(modifier = Modifier.height(20.dp))
            // Поле Пароль
            RoundedCornerTextField(
                label = "Password",
                text = passwordState.value
            ) {
                passwordState.value = it
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { /* Действие для выхода из профиля */ },
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
                onClick = { /* Действие для выхода из профиля */ },
                modifier = Modifier
                    .fillMaxWidth(0.5f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
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
