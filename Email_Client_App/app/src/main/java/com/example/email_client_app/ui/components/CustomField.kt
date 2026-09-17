package com.example.email_client_app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomField(
    label: String,
    value: String,
    onChange: (String) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.DarkGray
        )

        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                onChange(newValue)
            },

            modifier = Modifier
                .fillMaxWidth(),

            singleLine = true,

            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp
            ),

            colors = OutlinedTextFieldDefaults.colors(

                // INPUT TEXT
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                disabledTextColor = Color.Black,

                // CURSOR
                cursorColor = Color(0xFF4F46E5),

                // BACKGROUND
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,

                // BORDER
                focusedBorderColor = Color(0xFF4F46E5),
                unfocusedBorderColor = Color.Gray,

                // PLACEHOLDER
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray
            )
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )
    }
}