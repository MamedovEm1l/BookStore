package com.example.bookstore.composables
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.bookstore.ui.theme.ButtonColor

@Composable
fun LoginButton(
    text: String,
    modifier: Modifier = Modifier.fillMaxWidth(0.5f),
    onClick: () -> Unit
) {
    Button (
        onClick = {
        onClick()
    },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = ButtonColor
        )
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center
        )
    }
}