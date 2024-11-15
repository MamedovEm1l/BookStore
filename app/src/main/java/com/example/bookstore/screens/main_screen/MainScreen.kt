package com.example.bookstore.screens.main_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bookstore.R
import com.example.bookstore.data.Book
import com.example.bookstore.screens.login.Data.MainScreenDataObject
import com.example.bookstore.screens.main_screen.Composables.BottomMenu
import com.example.bookstore.ui.theme.ButtonColor
import com.example.bookstore.ui.theme.CreamColor
import com.example.bookstore.ui.theme.FilterColor2
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun MainScreen(
    navController: NavController = NavController(context = TODO()),
    navData: MainScreenDataObject = MainScreenDataObject("AfUv9PxdFjUtVyNtOGjpA2z98cX2","mamedovemil366@gmail.com"),
    onAdminClick: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val bookListState = remember { mutableStateOf(emptyList<Book>()) }

    LaunchedEffect(Unit) {
        val db = Firebase.firestore
        getAllBooks(db) { books ->
            bookListState.value = books
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = Modifier.fillMaxWidth(),
        drawerContent = {
            Column(
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                DrawerHeader(navData.email)
                DrawerBody(navData) {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    onAdminClick()
                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(CreamColor),
            topBar = {
                TopBar()
            },
            bottomBar = {
                BottomMenu(navController = navController)
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                SearchBar()
                CategorySection()
                PopularSection()
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .background(CreamColor)
                        .padding(8.dp)
                ) {
                    items(bookListState.value) { book ->
                        BookListItemUi(book, {})
                    }
                }
            }
        }
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(CreamColor)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { /* Open drawer */ }) {
            Icon(
                painter = painterResource(R.drawable.ic_home),
                contentDescription = "Menu Icon",
                tint = Color.Black
            )
        }
        Text(
            text = "Home",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        IconButton(onClick = { /* Open cart or notifications */ }) {
            Icon(
                painter = painterResource(R.drawable.store),
                contentDescription = "Cart Icon",
                tint = Color.Black
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    TextField(
        value = "",
        onValueChange = { /* Handle search text */ },
        placeholder = { Text("Поиск", color = Color.Gray) },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = "Search Icon",
                tint = Color.Gray
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(CreamColor)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = ButtonColor,
            unfocusedIndicatorColor = ButtonColor
        )
    )
}

@Composable
fun CategorySection() {
    Column(
        modifier = Modifier.background(CreamColor).fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Category",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CategoryButton("All")
            CategoryButton("Drama")
            CategoryButton("Comedy")
        }
    }
}

@Composable
fun CategoryButton(text: String) {
    Box(
        modifier = Modifier
            .background(CreamColor)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .clickable { /* Handle click */ }
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(text = text, color = Color.Black, fontSize = 14.sp)
    }
}

@Composable
fun PopularSection() {
    Row(
        modifier = Modifier
            .background(CreamColor)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Popular",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = "All",
            fontSize = 14.sp,
            color = Color.Blue,
            modifier = Modifier.clickable { /* Show all popular items */ }
        )
    }
}

private fun getAllBooks(
    db: FirebaseFirestore,
    onBooks: (List<Book>) -> Unit
) {
    db.collection("books")
        .get()
        .addOnSuccessListener { task ->
            onBooks(task.toObjects(Book::class.java))
        }
        .addOnFailureListener {
            // Handle errors
        }
}
