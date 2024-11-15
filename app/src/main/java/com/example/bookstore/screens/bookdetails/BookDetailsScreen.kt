package com.example.bookstore.screens.bookdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.bookstore.data.Book
import com.example.bookstore.ui.theme.ButtonColor

@Preview(showBackground = true)
@Composable
fun BookDetailsScreen(book: Book = Book(key = "12","dgh","hiffg","100", "drama",  "https://firebasestorage.googleapis.com/v0/b/bookstore-d9c83.appspot.com/o/book_images%2Fimage_1731497939863.jpg?alt=media&token=e77e8a72-7456-4d49-a8bb-404697be2092" )
) {
    Image(
        painter = rememberAsyncImagePainter(
            model = "https://firebasestorage.googleapis.com/v0/b/bookstore-d9c83.appspot.com/o/book_images%2Fimage_1731498033781.jpg?alt=media&token=dce069a3-1b7c-4f03-82a4-5a6fd3c072e9"
        ),
        contentDescription = "BG",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        alpha = 0.4f
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = book.imageUrl,
            contentDescription = book.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "The Home In Lazur",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = ButtonColor
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Автор: Emil", fontSize = 25.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Описание: Any time in life be anything along men in the street home when be along today three line ago today when going to the club he see what she do ",
            fontSize = 23.sp,
            textAlign = TextAlign.Center)
        //Text(text = book.description)
    }
}
