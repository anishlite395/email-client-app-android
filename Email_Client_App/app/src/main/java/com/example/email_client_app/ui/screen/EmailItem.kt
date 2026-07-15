package com.example.email_client_app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmailItem( sender: String, subject: String, time: String,onClick : () -> Unit){
    Column(modifier = Modifier.fillMaxWidth()
        .clickable{
            onClick()
        }
        .background(Color.White)
        .padding(horizontal = 12.dp, vertical = 12.dp)) {

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween){

            Text(text = sender,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp)

            Text(text = time,
                color = Color.Gray,
                fontSize = 12.sp)

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = subject,
                fontSize = 14.sp,
                color = Color.DarkGray)

        }
    }

}