package com.example.bookstore.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.bookstore.R
import com.example.bookstore.composables.LoginButton
import com.example.bookstore.composables.RoundedCornerDropDownMenu
import com.example.bookstore.composables.RoundedCornerTextField
import com.example.bookstore.ui.theme.BoxFilterColor
import com.example.bookstore.ui.theme.ButtonColor
import com.example.bookstore.viewmodel.AddBookViewModel

@Composable
fun AddBookScreen(
    viewModel: AddBookViewModel = viewModel(),
    bookId: String? = null,
    onSaved: () -> Unit = {}
) {
    LaunchedEffect(bookId) {
        if (bookId != null) {
            viewModel.loadBookData(bookId)
        }
    }
    // Collect state from ViewModel
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val title by viewModel.title.collectAsState()
    val author by viewModel.author.collectAsState()
    val description by viewModel.description.collectAsState()
    val publicationDate by viewModel.publicationDate.collectAsState()
    val publisher by viewModel.publisher.collectAsState()
    val isStock by viewModel.isStock.collectAsState()
    val quantity by viewModel.quantity.collectAsState()
    val price by viewModel.price.collectAsState()
    val selectedImageUri by viewModel.selectedImageUri.collectAsState()

    val categoriesList = listOf("Fantasy", "Drama", "Bestsellers")

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.setImageUri(uri)
        }
    }

    Image(
        painter = rememberAsyncImagePainter(model = selectedImageUri),
        contentDescription = "Background Image",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        alpha = 0.4f
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BoxFilterColor)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 50.dp, end = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.books),
            contentDescription = "Logo",
            modifier = Modifier.size(70.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Add new book",
            color = ButtonColor,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            fontFamily = FontFamily.Serif
        )
        Spacer(modifier = Modifier.height(15.dp))
        RoundedCornerDropDownMenu(
            items = categoriesList,
            selectedItem = selectedCategory,
            onItemSelected = { viewModel.updateCategory(it) }
        )
        Spacer(modifier = Modifier.height(15.dp))
        RoundedCornerTextField(
            text = title,
            label = "Title",
            onValueChange = { viewModel.updateField("title", it) }
        )
        Spacer(modifier = Modifier.height(10.dp))
        RoundedCornerTextField(
            singleLine = false,
            text = author,
            label = "Author",
            onValueChange = { viewModel.updateField("author", it) }
        )
        Spacer(modifier = Modifier.height(10.dp))
        RoundedCornerTextField(
            maxLines = 3,
            singleLine = false,
            text = description,
            label = "Description",
            onValueChange = { viewModel.updateField("description", it) }
        )
        Spacer(modifier = Modifier.height(10.dp))
        RoundedCornerTextField(
            singleLine = false,
            text = publicationDate,
            label = "Publication Date",
            onValueChange = { viewModel.updateField("publicationDate", it) }
        )
        Spacer(modifier = Modifier.height(10.dp))
        RoundedCornerTextField(
            singleLine = false,
            text = publisher,
            label = "Publisher",
            onValueChange = { viewModel.updateField("publisher", it) }
        )
        Spacer(modifier = Modifier.height(10.dp))
        RoundedCornerDropDownMenu(
            items = listOf("true", "false"),
            selectedItem = isStock,
            onItemSelected = { viewModel.updateIsStock(it) }
        )
        Spacer(modifier = Modifier.height(10.dp))
        RoundedCornerTextField(
            text = quantity,
            label = "Quantity",
            onValueChange = { if (isStock.toBoolean()) viewModel.updateField("quantity", it) }
        )
        Spacer(modifier = Modifier.height(10.dp))
        RoundedCornerTextField(
            text = price,
            label = "Price",
            onValueChange = { viewModel.updateField("price", it) }
        )
        Spacer(modifier = Modifier.height(10.dp))
        LoginButton(text = "Select image") {
            imageLauncher.launch("image/*")
        }
        LoginButton(text = "Save") {
            if (bookId != null) {
                viewModel.updateBook(bookId, onSaved, { error -> /* Обработка ошибки */ })
            } else {
                viewModel.saveBook(onSaved, { error -> /* Обработка ошибки */ })
            }
        }
    }
}
