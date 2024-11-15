package com.example.bookstore.screens.main_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.bookstore.R
import com.example.bookstore.data.Book
import com.example.bookstore.ui.theme.ButtonColor
import com.example.bookstore.ui.theme.CreamColor

//@Preview(showBackground = true)
@Composable
fun BookListItemUi(book: Book, onClick: (Book) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        AsyncImage(
            model = book.imageUrl,
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = book.title,
            color = CreamColor,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = book.description,
            color = Color.White,
            fontSize = 16.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = book.price,
            color = Color.Blue,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}