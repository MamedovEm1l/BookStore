package com.example.bookstore.screens.add_book_screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.bookstore.ui.theme.ButtonColor
import com.example.bookstore.ui.theme.FilterColor2

@Composable
fun RoundedCornerDropDownMenu(
    onCategorySelected: (String) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    val selectedCategory = remember { mutableStateOf("Drama") }
    val categoriesList = listOf(
        "Fantasy",
        "Drama",
        "Bestsellers"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = ButtonColor,
                shape = RoundedCornerShape(25.dp)
            )
            .clip(RoundedCornerShape(25.dp))
            .background(FilterColor2)
            .clickable {
                expanded.value = true
            }
            .padding(15.dp)
    ) {
        Text(text = selectedCategory.value)
        DropdownMenu(expanded = expanded.value,
            onDismissRequest = {
                expanded.value = false
            }) {
            categoriesList.forEach { category ->
                DropdownMenuItem(text = {
                    Text(text = category)
                }, onClick = {
                    selectedCategory.value = category
                    expanded.value = false
                    onCategorySelected(category)
                })
            }
        }
    }
}