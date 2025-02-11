package com.example.bookstore.screens.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bookstore.R
import com.example.bookstore.composables.BookListItemUi
import com.example.bookstore.composables.BottomMenu
import com.example.bookstore.composables.FilterSection
import com.example.bookstore.model.Book
import com.example.bookstore.model.Screen
import com.example.bookstore.screens.main_screen.bottom_menu.BottomMenuItem
import com.example.bookstore.ui.theme.ButtonColor
import com.example.bookstore.ui.theme.CreamColor
import com.example.bookstore.viewmodel.MainViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navController: NavController,
    onBookEditClick: (Book) -> Unit,
    mainViewModel: MainViewModel = viewModel(),
    onAdminClick: () -> Unit = {}
) {
    val books by mainViewModel.bookList.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val maxCardHeight = remember { mutableStateOf(0.dp) }
    val selectedBottomItemState = remember {
        mutableStateOf(BottomMenuItem.Home.title)
    }

    val searchQuery = remember { mutableStateOf("") }
    val openDeleteDialog = remember { mutableStateOf(false) }
    val bookToDelete = remember { mutableStateOf<Book?>(null) }

    LaunchedEffect(Unit) {
       mainViewModel.loadBooks()
    }

    if (openDeleteDialog.value && bookToDelete.value != null) {
        AlertDialog(
            onDismissRequest = { openDeleteDialog.value = false },
            title = { Text("Удалить книгу?") },
            text = { Text("Вы уверены, что хотите удалить эту книгу?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Удаляем книгу из базы данных
                        bookToDelete.value?.let { mainViewModel.deleteBook(it) }
                        openDeleteDialog.value = false
                    }
                ) {
                    Text("Удалить")
                }
            },
            dismissButton = {
                TextButton(onClick = { openDeleteDialog.value = false }) {
                    Text("Отмена")
                }
            }
        )
    }


    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = Modifier.fillMaxWidth(),
        drawerContent = {
            Column(
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                DrawerHeader(Firebase.auth.currentUser?.email ?: "Guest")
                DrawerBody(mainViewModel, navController) {
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
                TopBar(navController)
            },
            bottomBar = {
                BottomMenu(navController = navController, selectedBottomItemState.value)
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                SearchBar(
                    searchQuery.value,
                    onQueryChange = { query ->
                        searchQuery.value = query
                        mainViewModel.searchBooks(query)
                    }
                )
                FilterSection(viewModel = mainViewModel)
                CategorySection()
                PopularSection()
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .background(CreamColor)
                        .padding(8.dp)
                ) {
                    items(books) { book ->
                        BookListItemUi(
                            book = book,
                            onEditClick = { onBookEditClick(book) },
                            onDeleteClick = {
                                bookToDelete.value = book
                                openDeleteDialog.value = true
                            },
                            modifier = Modifier
                                .fillMaxHeight()
                                .onGloballyPositioned { coordinates ->
                                    val height = coordinates.size.height.dp
                                    if (height > maxCardHeight.value) {
                                        maxCardHeight.value = height
                                    }
                                }
                        ) { selectedBook ->
                            navController.navigate(Screen.BookDetails.createRoute(selectedBook.key))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopBar(navController:NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(CreamColor)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { navController.navigate(Screen.Main.route) }) {
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
        IconButton(onClick = { navController.navigate(Screen.Basket.route)  }) {
            Icon(
                painter = painterResource(R.drawable.store),
                contentDescription = "Cart Icon",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun SearchBar( query: String,
               onQueryChange: (String) -> Unit
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
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
        colors = TextFieldDefaults.colors(
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