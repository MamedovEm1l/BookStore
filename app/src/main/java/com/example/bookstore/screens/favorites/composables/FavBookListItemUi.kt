package com.example.bookstore.screens.favorites.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.bookstore.data.Book
import com.example.bookstore.R // Убедитесь, что путь к ресурсам корректен
import com.example.bookstore.ui.theme.ButtonColor
import com.example.bookstore.ui.theme.CreamColor

@Composable
fun FavBookListItemUi(
    book: Book,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick() }
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = book.imageUrl,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )

            // Иконка "сердце" в левом верхнем углу
            Icon(
                painter = painterResource(id = R.drawable.ic_fav), // Замените на реальный ресурс сердца
                contentDescription = "Favorite",
                tint = Color.Red,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.TopStart)
                    .padding(8.dp)
                    .clip(CircleShape)
            )

        }

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
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = book.price,
                color = Color.Blue,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Icon(
                painter = painterResource(id = R.drawable.store), // Замените на реальный ресурс корзины
                contentDescription = "Add to Cart",
                tint = CreamColor,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
            )
        }
    }
}
