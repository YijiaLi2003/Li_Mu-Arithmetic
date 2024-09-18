package com.example.li_mu_arithmetic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.li_mu_arithmetic.ui.theme.Li_MuArithmeticTheme
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Li_MuArithmeticTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ArithmeticCalculator(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ArithmeticCalculator(modifier: Modifier = Modifier) {
    var operand1 by remember { mutableStateOf("") }
    var operand2 by remember { mutableStateOf("") }
    var selectedOperation by remember { mutableStateOf("Addition") }
    var result by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val operations = listOf("Addition", "Subtraction", "Multiplication", "Division", "Modulus")

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Simple Arithmetic Calculator", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = operand1,
            onValueChange = { operand1 = it },
            label = { Text("Operand 1") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = operand2,
            onValueChange = { operand2 = it },
            label = { Text("Operand 2") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        Text(text = "Select Operation:")

        operations.forEach { operation ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedOperation == operation,
                    onClick = { selectedOperation = operation }
                )
                Text(text = operation)
            }
        }

        Button(onClick = {
            errorMessage = null
            result = null

            val num1 = operand1.toDoubleOrNull()
            val num2 = operand2.toDoubleOrNull()

            if (num1 == null || num2 == null) {
                errorMessage = "Please enter valid numbers."
                return@Button
            }

            result = when (selectedOperation) {
                "Addition" -> "${num1 + num2}"
                "Subtraction" -> "${num1 - num2}"
                "Multiplication" -> "${num1 * num2}"
                "Division" -> {
                    if (num2 == 0.0) {
                        errorMessage = "Uh-oh! Division by zero is not allowed."
                        null
                    } else {
                        "${num1 / num2}"
                    }
                }
                "Modulus" -> {
                    if (num2 == 0.0) {
                        errorMessage = "Oops! Cannot perform modulus by zero."
                        null
                    } else {
                        "${num1 % num2}"
                    }
                }
                else -> {
                    errorMessage = "Unknown operation selected."
                    null
                }
            }
        }) {
            Text(text = "Calculate")
        }

        result?.let {
            Text(text = "Result: $it", style = MaterialTheme.typography.bodyLarge)
        }

        errorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArithmeticCalculatorPreview() {
    Li_MuArithmeticTheme {
        ArithmeticCalculator()
    }
}
