package com.example.bookstore.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookstore.R
import com.example.bookstore.ui.theme.ButtonColor
import com.example.bookstore.ui.theme.CreamColor
import com.example.bookstore.viewmodel.MainViewModel

@Composable
fun FilterSection(
    viewModel: MainViewModel
) {
    val expanded = remember { mutableStateOf(false) }
    val selectedGenre = remember { mutableStateOf<String?>(null) }
    val selectedPriceRange = remember { mutableStateOf<IntRange?>(null) }
    val isStockChecked = remember { mutableStateOf<Boolean?>(null) }
    val filterState by viewModel.filterState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(CreamColor)
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded.value = !expanded.value }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Фильтры",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 16.sp
            )
            Icon(
                modifier = Modifier.size(25.dp),
                painter = painterResource(
                    if (expanded.value) R.drawable.minus
                    else R.drawable.filter
                ),
                contentDescription = null
            )
        }

        if (expanded.value) {
            Column(
                modifier = Modifier
                    .background(CreamColor)
                    .border(1.dp, ButtonColor, RoundedCornerShape(25.dp))
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Жанр:", fontWeight = FontWeight.Bold, color = Color.Black)
                GenreDropdown(
                    genres = listOf("All", "Fantasy", "Drama", "Bestsellers"),
                    selectedGenre = filterState.genre,
                    onGenreSelected = { genre ->
                        selectedGenre.value = genre
                        viewModel.applyFilter(
                            genre = if (genre == "All") null else selectedGenre.value,
                            priceRange = selectedPriceRange.value,
                            isInStock = isStockChecked.value
                        )
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Цена:", fontWeight = FontWeight.Bold, color = Color.Black)
                PriceRangeSlider(
                    range = 0..5000,
                    onValueChange = { range ->
                        selectedPriceRange.value = range
                        viewModel.applyFilter(
                            genre = selectedGenre.value,
                            priceRange = selectedPriceRange.value,
                            isInStock = isStockChecked.value
                        )
                    }
                )

                // Фильтр по наличию
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "В наличии:", fontWeight = FontWeight.Bold, color = Color.Black)
                StockCheckboxGroup(
                    isStockChecked = isStockChecked.value,
                    onStockChange = { isChecked ->
                        isStockChecked.value = isChecked
                        viewModel.applyFilter(
                            genre = selectedGenre.value,
                            priceRange = selectedPriceRange.value,
                            isInStock = isStockChecked.value
                        )
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))
                LoginButton(
                    "Применить"
                ) {
                    expanded.value = false
                    viewModel.applyFilter(
                        genre = selectedGenre.value,
                        priceRange = selectedPriceRange.value,
                        isInStock = isStockChecked.value
                    )
                }
            }
        }
    }
}

@Composable
fun GenreDropdown(
    genres: List<String>,
    selectedGenre: String?,
    onGenreSelected: (String) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { expanded.value = !expanded.value }
            .background(Color.LightGray.copy(alpha = 0.3f))
            .clip(RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Text(
            text = selectedGenre ?: "Выберите жанр",
            color = Color.Black,
            modifier = Modifier.fillMaxWidth()
        )

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            genres.forEach { genre ->
                DropdownMenuItem(
                    onClick = {
                        expanded.value = false
                        onGenreSelected(genre)
                    },
                    text = { Text(text = genre) }
                )
            }
        }
    }
}


@Composable
fun StockCheckboxGroup(
    isStockChecked: Boolean?,
    onStockChange: (Boolean) -> Unit
) {
    Row {
        Text(
            text = "Да",
            modifier = Modifier
                .clickable { onStockChange(true) }
                .padding(8.dp),
            color = if (isStockChecked == true) Color.Blue else Color.Black
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Нет",
            modifier = Modifier
                .clickable { onStockChange(false) }
                .padding(8.dp),
            color = if (isStockChecked == false) Color.Blue else Color.Black
        )
    }
}
