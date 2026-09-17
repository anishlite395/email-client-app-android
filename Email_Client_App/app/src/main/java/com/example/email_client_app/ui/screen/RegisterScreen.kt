//package com.example.email_client_app.ui.screen
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.imePadding
//import androidx.compose.foundation.layout.navigationBarsPadding
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Checkbox
//import androidx.compose.material3.OutlinedButton
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.modifier.modifierLocalConsumer
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.email_client_app.ui.components.CustomField
//import com.example.email_client_app.ui.components.PasswordField
//import com.example.email_client_app.ui.viewmodel.AuthViewModel
//
//@Composable
//fun RegisterScreen(viewModel: AuthViewModel = hiltViewModel(),
//                   onNavigateToLogin: () -> Unit)
//{
//
//    val state = viewModel.uiState
//
//
//    val gradient = Brush.horizontalGradient(
//        colors = listOf(
//            Color(0xFF4F46E5),
//            Color(0xFF7C3AED)
//        )
//    )
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFFF7F8FA))
//            .verticalScroll(rememberScrollState())
//            .imePadding()
//            .navigationBarsPadding()
//            .padding(horizontal = 24.dp, vertical = 20.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ){
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        Text("MailBox", fontSize = 20.sp, color = Color.Gray)
//
//        Text("Create Account", fontSize = 26.sp, color = Color.Black)
//
//        Text("Join us to get started", fontSize = 14.sp, color = Color.Gray)
//
//        Spacer(modifier = Modifier.height(30.dp))
//
//        CustomField(label="Full Name", value = state.name, onChange = viewModel::onNameChange)
//        CustomField(label="Email Address", value = state.email, onChange = viewModel::onEmailChange)
//        PasswordField("Password",state.password, onChange = viewModel::onPasswordChange)
//        PasswordField("Confirm Password",state.confirmPassword, onChange = viewModel::onPConfirmPasswordChange)
//        CustomField(label = "Location", value = state.location, onChange = viewModel::onLocationChange)
//        Spacer(modifier = Modifier.height(10.dp))
//
//        Row(verticalAlignment = Alignment.CenterVertically ){
//            Checkbox(checked = state.agreed,
//                onCheckedChange = viewModel::onAgreementChange)
//            Text("I agree to Terms & Private Policy",
//                fontSize = 13.sp)
//        }
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        Button(
//            onClick = {
//                viewModel.Register()
//            },
//            modifier = Modifier.fillMaxWidth().height(50.dp),
//            shape = RoundedCornerShape(12.dp),
//            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
//
//        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(50.dp)
//                    .background(
//                        brush = gradient,
//                        shape = RoundedCornerShape(12.dp)
//                    )
//                    .clickable{
//                        viewModel.Register()
//                    },
//                contentAlignment = Alignment.Center
//            ){
//                Text("CREATE ACCOUNT", color = Color.White)
//            }
//        }
//
//
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        // Error Message
//        state.error?.let {
//            Text(
//                text = it,
//                color = Color.Red,
//                fontSize = 14.sp
//            )
//        }
//
//        Text("OR", color = Color.Gray)
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        Row(
//            horizontalArrangement = Arrangement.spacedBy(12.dp),
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            OutlinedButton(
//                onClick = {},
//                modifier = Modifier.weight(1f)) {
//                Text("Google") }
//
//            OutlinedButton(onClick = {},
//                modifier = Modifier.weight(1f)) {
//                Text("Apple")
//            }
//        }
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        Text("Already have an account? Sign In",
//            color = Color(0xFF4F46E5),
//            modifier = Modifier
//                .clickable{
//                    onNavigateToLogin()
//                }
//                .padding(vertical = 12.dp,
//                         horizontal = 8.dp))
//
//    }
//}
//

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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.email_client_app.ui.components.CustomField
import com.example.email_client_app.ui.components.PasswordField
import com.example.email_client_app.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit
) {

    val state = viewModel.uiState

    // ----------------------------------
    // Gradient
    // ----------------------------------

    val gradient = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF4F46E5),
            Color(0xFF7C3AED)
        )
    )

    // ----------------------------------
    // Navigate to Login after success
    // ----------------------------------

    LaunchedEffect(state.successMessage) {

        if (state.successMessage != null) {

            delay(1500)

            onNavigateToLogin()
        }
    }

    // ----------------------------------
    // Main screen
    // ----------------------------------

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8FA))
            .verticalScroll(
                rememberScrollState()
            )
            .imePadding()
            .navigationBarsPadding()
            .padding(
                horizontal = 24.dp,
                vertical = 20.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ----------------------------------
        // Header
        // ----------------------------------

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "MailBox",
            fontSize = 20.sp,
            color = Color.Gray
        )

        Text(
            text = "Create Account",
            fontSize = 26.sp,
            color = Color.Black
        )

        Text(
            text = "Join us to get started",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        // ----------------------------------
        // Full Name
        // ----------------------------------

        CustomField(
            label = "Full Name",
            value = state.name,
            onChange = viewModel::onNameChange
        )

        // ----------------------------------
        // Email
        // ----------------------------------

        CustomField(
            label = "Email Address",
            value = state.email,
            onChange = viewModel::onEmailChange
        )

        // ----------------------------------
        // Password
        // ----------------------------------

        PasswordField(
            label = "Password",
            value = state.password,
            onChange = viewModel::onPasswordChange
        )

        // ----------------------------------
        // Confirm Password
        // ----------------------------------

        PasswordField(
            label = "Confirm Password",
            value = state.confirmPassword,
            onChange = viewModel::onPConfirmPasswordChange
        )

        // ----------------------------------
        // Location
        // ----------------------------------

        CustomField(
            label = "Location",
            value = state.location,
            onChange = viewModel::onLocationChange
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        // ----------------------------------
        // Terms and Privacy
        // ----------------------------------

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Checkbox(
                checked = state.agreed,
                onCheckedChange = viewModel::onAgreementChange
            )

            Text(
                text = "I agree to Terms & Privacy Policy",
                fontSize = 13.sp
            )
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        // ----------------------------------
        // Create Account Button
        // ----------------------------------

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(
                    brush = gradient,
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable(
                    enabled = !state.isloading
                ) {
                    viewModel.Register()
                },
            contentAlignment = Alignment.Center
        ) {

            if (state.isloading) {

                CircularProgressIndicator(
                    color = Color.White
                )

            } else {

                Text(
                    text = "CREATE ACCOUNT",
                    color = Color.White
                )
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        // ----------------------------------
        // Error Message
        // ----------------------------------

        state.error?.let {

            Text(
                text = it,
                color = Color.Red,
                fontSize = 14.sp
            )
        }

        // ----------------------------------
        // Success Message
        // ----------------------------------

        state.successMessage?.let {

            Text(
                text = "✓ $it",
                color = Color(0xFF16A34A),
                fontSize = 14.sp
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        // ----------------------------------
        // OR
        // ----------------------------------

        Text(
            text = "OR",
            color = Color.Gray
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        // ----------------------------------
        // Google / Apple
        // ----------------------------------

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            OutlinedButton(
                onClick = {
                    // TODO: Google authentication
                },
                modifier = Modifier.weight(1f),
                enabled = !state.isloading
            ) {

                Text(
                    text = "Google"
                )
            }

            OutlinedButton(
                onClick = {
                    // TODO: Apple authentication
                },
                modifier = Modifier.weight(1f),
                enabled = !state.isloading
            ) {

                Text(
                    text = "Apple"
                )
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        // ----------------------------------
        // Login
        // ----------------------------------

        Text(
            text = "Already have an account? Sign In",
            color = Color(0xFF4F46E5),
            modifier = Modifier
                .clickable {
                    onNavigateToLogin()
                }
                .padding(
                    vertical = 12.dp,
                    horizontal = 8.dp
                )
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )
    }
}