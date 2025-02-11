package com.example.bookstore.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookstore.ui.theme.ButtonColor

@Preview(showBackground = true)
@Composable
fun PriceRangeSlider(
    range: IntRange = 0..5000,
    onValueChange: (IntRange) -> Unit = TODO()
) {
    val expanded = remember { mutableStateOf(false) }
    val minValue = remember { mutableFloatStateOf(range.first.toFloat()) }
    val maxValue = remember { mutableFloatStateOf(range.last.toFloat()) }

    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Text(
            text = "Цена: ${minValue.floatValue.toInt()} - ${maxValue.floatValue.toInt()} ₽",
            modifier = Modifier
                .clickable { expanded.value = !expanded.value }
                .padding(vertical = 8.dp),
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        androidx.compose.material3.DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = "Выберите диапазон цен",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Slider(
                        value = minValue.floatValue,
                        onValueChange = {
                            if (it <= maxValue.floatValue) {
                                minValue.floatValue = it
                                onValueChange(minValue.floatValue.toInt()..maxValue.floatValue.toInt())
                            }
                        },
                        valueRange = range.first.toFloat()..range.last.toFloat(),
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Slider(
                        value = maxValue.floatValue,
                        onValueChange = {
                            if (it >= minValue.floatValue) {
                                maxValue.floatValue = it
                                onValueChange(minValue.floatValue.toInt()..maxValue.floatValue.toInt())
                            }
                        },
                        valueRange = range.first.toFloat()..range.last.toFloat(),
                        modifier = Modifier.weight(1f)
                    )
                }
                Button(
                    onClick = { expanded.value = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = ButtonColor)
                ) {
                    Text(text = "Применить", color = Color.White)
                }
            }
        }
    }
}