package com.example.bookstore.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookstore.composables.LoginButton
import com.example.bookstore.composables.RoundedCornerTextField
import com.example.bookstore.model.Order
import com.example.bookstore.ui.theme.CreamColor
import com.example.bookstore.viewmodel.BasketViewModel
import com.example.bookstore.viewmodel.OrderViewModel
import kotlinx.coroutines.launch

@Composable
fun OrderFormScreen(
    basketViewModel: BasketViewModel,
    orderViewModel: OrderViewModel,
    onOrderComplete: () -> Unit
) {
    val basket by basketViewModel.basket.collectAsState()
    var address by remember { mutableStateOf("") }
    var deliveryDate by remember { mutableStateOf("") }
    var deliveryTime by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val totalCost =  remember(basket) {
        basket.sumOf {
            it.book.price.dropLast(2).toDoubleOrNull()?.times(it.quantity) ?: 0.0
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Доставка",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 50.dp) // Отступ снизу, чтобы не слипалось с элементами ниже
        )
        RoundedCornerTextField(
            text = address,
            onValueChange = { address = it },
            label = "Адрес доставки",
        )
        Spacer(modifier = Modifier.height(10.dp))
        RoundedCornerTextField(
            text = deliveryDate,
            onValueChange = { deliveryDate = it },
            label = "Дата доставки"
        )
        Spacer(modifier = Modifier.height(10.dp))
        RoundedCornerTextField(
            text = deliveryTime,
            onValueChange = { deliveryTime = it },
            label = "Время доставки"
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text("Итого:    ${"%.2f".format(totalCost)} руб.", fontSize = 20.sp, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(10.dp))

        errorMessage?.let {
            Text(
                text = it,
                color = androidx.compose.ui.graphics.Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        LoginButton("Оформить заказ")
        {
            if (address.isBlank() || deliveryDate.isBlank() || deliveryTime.isBlank()) {
                errorMessage = "Заполните все поля!"
                return@LoginButton
            }
            coroutineScope.launch {
                try {
                    val order = Order(
                        address = address,
                        date = deliveryDate,
                        time = deliveryTime,
                        totalPrice = totalCost,
                        items = basketViewModel.basket.value // Передача корзины
                    )
                    orderViewModel.saveOrder(order)
                    basketViewModel.clearBasket() // Очищаем корзину после успешного заказа
                    onOrderComplete()
                } catch (e: Exception) {
                    errorMessage = "Ошибка при сохранении заказа. Попробуйте снова."
                    Log.e("OrderFormScreen", "Error saving order: ${e.localizedMessage}")
                }
            }
        }
    }
}
