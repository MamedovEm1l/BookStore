package com.example.bookstore.screens.main_screen

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookstore.R
import com.example.bookstore.screens.login.Data.MainScreenDataObject
import com.example.bookstore.screens.main_screen.Composables.Item
import com.example.bookstore.ui.theme.ButtonColor
import com.example.bookstore.ui.theme.CreamColor
import com.example.bookstore.ui.theme.LightCreamColor
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

//@Preview(showBackground = true)
@Composable
fun DrawerBody(
    navData: MainScreenDataObject,
    onAdminClick: () -> Unit = {}
) {
    val categoriesList = listOf(
        "Favorites",
        "Fantasy",
        "Drama",
        "Bestsellers"
    )

    val isAdminState = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        isAdmin(navData) { isAdmin ->
            isAdminState.value = isAdmin
        }
    }
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
            if (isAdminState.value)
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
    }
}

fun isAdmin(navData: MainScreenDataObject, onAdmin: (Boolean) -> Unit) {
    Firebase.firestore.collection("admin")
        .document(navData.uid).get().addOnSuccessListener {
        onAdmin(it.get("isAdmin") as Boolean)
    }
}