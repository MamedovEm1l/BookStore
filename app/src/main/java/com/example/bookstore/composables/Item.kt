package com.example.bookstore.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookstore.ui.theme.LightCreamColor

@Composable
fun Item(
    item:String
) {
    Spacer (modifier = Modifier.height(10.dp))
    Text(
        text = item,
        color = Color.Black,
        fontSize = 20.sp,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth()
    )
    Spacer (modifier = Modifier.height(10.dp))
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(LightCreamColor)
    )
}