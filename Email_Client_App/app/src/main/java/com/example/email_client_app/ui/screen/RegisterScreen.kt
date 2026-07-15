package com.example.email_client_app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.email_client_app.ui.components.CustomField
import com.example.email_client_app.ui.components.PasswordField
import com.example.email_client_app.ui.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(viewModel: AuthViewModel = hiltViewModel(),
                   onNavigateToLogin: () -> Unit)
{

    val state = viewModel.uiState


    val gradient = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF4F46E5),
            Color(0xFF7C3AED)
        )
    )

    Column(
        modifier = Modifier.fillMaxSize()
            .background(Color(0xFFF7F8FA))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Spacer(modifier = Modifier.height(30.dp))

        Text("MailBox", fontSize = 20.sp, color = Color.Gray)

        Text("Create Account", fontSize = 26.sp, color = Color.Black)

        Text("Join us to get started", fontSize = 14.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(30.dp))

        CustomField(label="Full Name", value = state.name, onChange = viewModel::onNameChange)
        CustomField(label="Email Address", value = state.email, onChange = viewModel::onEmailChange)
        PasswordField("Password",state.password, onChange = viewModel::onPasswordChange)
        PasswordField("Confirm Password",state.confirmPassword, onChange = viewModel::onPConfirmPasswordChange)
        CustomField(label = "Location", value = state.location, onChange = viewModel::onLocationChange)
        Spacer(modifier = Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically ){
            Checkbox(checked = state.agreed, onCheckedChange = viewModel::onAgreementChange)
            Text("I agee to Terms & Private Policy", fontSize = 13.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.Register()
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)

        ) {
            Box(
                modifier = Modifier.fillMaxSize().background(gradient),
                contentAlignment = Alignment.Center
            ){
                Text("CREATE ACCOUNT", color = Color.White)
            }
        }



        Spacer(modifier = Modifier.height(20.dp))

        // Error Message
        state.error?.let {
            Text(
                text = it,
                color = Color.Red,
                fontSize = 14.sp
            )
        }

        Text("OR", color = Color.Gray)

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedButton(onClick = {}) {
                Text("Google") }

            OutlinedButton(onClick = {}) {
                Text("Apple")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Already have an account? Sign In", color = Color(0xFF4F46E5),
            modifier = Modifier.padding(top = 10.dp)
                .clickable{
                    onNavigateToLogin()
                })

    }
}