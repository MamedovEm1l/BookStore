package com.example.bookstore.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.bookstore.ui.theme.ButtonColor
import com.example.bookstore.ui.theme.FilterColor2

@Composable
fun RoundedCornerTextField(
    modifier: Modifier = Modifier.fillMaxWidth().border(1.dp, ButtonColor, RoundedCornerShape(25.dp)),
    maxLines: Int = 1,
    singleLine: Boolean = true,
    text: String,
    label: String,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        value = text, onValueChange = {
        onValueChange(it)
    },
        shape = RoundedCornerShape(25.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = FilterColor2,
            focusedContainerColor = FilterColor2,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        modifier = modifier,
        visualTransformation = visualTransformation,
        label = {
            Text(text = label, color = ButtonColor)
        },
        singleLine = singleLine,
        maxLines = maxLines
    )
}