package com.example.bookstore.screens.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bookstore.R
import com.example.bookstore.composables.Item
import com.example.bookstore.model.Screen
import com.example.bookstore.ui.theme.ButtonColor
import com.example.bookstore.ui.theme.CreamColor
import com.example.bookstore.ui.theme.LightCreamColor
import com.example.bookstore.viewmodel.LoginViewModel
import com.example.bookstore.viewmodel.MainViewModel
import com.example.bookstore.viewmodel.RegisterViewModel

@Composable
fun DrawerBody(
    mainViewModel: MainViewModel = viewModel(),
    navController: NavController,
    onAdminClick: () -> Unit = {}
) {
    val loginViewModel: LoginViewModel = viewModel()
    val registerViewModel: RegisterViewModel = viewModel()

    val categoriesList = listOf(
        "Favorites",
        "Fantasy",
        "Drama",
        "Bestsellers"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamColor)
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.book_store_bg),
            contentDescription = "",
            alpha = 0.3f,
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Categories",
                fontSize = 23.sp,
                color = ButtonColor,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(LightCreamColor)
            )
            LazyColumn(Modifier.fillMaxWidth()) {
                items(categoriesList) { item ->
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .clickable {}) {
                        Item(item)
                    }
                }
            }
            if (mainViewModel.isAdminState.collectAsState().value){
                Button(
                    onClick = {
                        onAdminClick()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonColor
                    )
                ) {
                    Text(text = "Admin panel")
                }
            }
            Button(
                onClick = {
                    mainViewModel.logout(
                        loginViewModel = loginViewModel,
                        registerViewModel = registerViewModel
                    ) {
                        navController.navigate(Screen.Login.route)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonColor
                )
            ) {
                Text(text = "Exit")
            }
        }
    }
}

