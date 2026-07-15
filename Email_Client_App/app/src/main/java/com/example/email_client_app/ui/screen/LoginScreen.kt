import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.email_client_app.data.local.TokenManager
import com.example.email_client_app.ui.components.CustomField
import com.example.email_client_app.ui.components.PasswordField
import com.example.email_client_app.ui.viewmodel.LoginViewModel

@Composable
fun LoginScreen(onNavigateToRegister: () -> Unit,
                onLoginSuccess: (String) -> Unit){

    val context = LocalContext.current

    val tokenManager = remember{
        TokenManager(context)
    }

    val viewModel = remember {
        LoginViewModel(tokenManager)
    }

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
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Text("MailBox",fontSize = 20.sp, color = Color.Gray)

        Text("Welcome Back", fontSize = 26.sp, color = Color.Black)

        Text("Good to See You Again", fontSize = 14.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(40.dp))

        //Email Field
        CustomField(label = "Email Address", value = state.email, onChange = viewModel::onEmailChange)

        Spacer(modifier = Modifier.height(10.dp))

        //Password Field
        PasswordField(label = "Password", value = state.password, onChange = viewModel::onPasswordChange)

        Spacer(modifier = Modifier.height(10.dp))

        //Remember + Forgot Password Flow
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Checkbox(
                    checked = state.agreed,
                    onCheckedChange = viewModel::onAgreedChange
                )
                Text("Remember Me", fontSize = 13.sp)
            }

            Text(
                text = "Forgot Password",
                color = Color(0xFF4F46E5),
                fontSize = 13.sp)

        }

        Spacer(modifier = Modifier.height(25.dp))

        //LOGIN BUTTON

        Button(
            onClick = {
                viewModel.login() {
                    onLoginSuccess(state.email)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),

            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(gradient),
                contentAlignment = Alignment.Center
            ) {
                if(state.isLoading){

                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier.height(20.dp)
                    )
                }else{

                    Text(
                        "LOGIN",
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        //Error
        state.error?.let {
            Text(
                text = it,
                color = Color.Red,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        //Social Login
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ){
            OutlinedButton(onClick = {}) {
                Text("Google")
            }

            OutlinedButton(onClick = {}) {
                Text("Apple")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Navigate to Register
        Text(
            text = "Don't have an account? Sign Up",
            color = Color(0xFF4F46E5),
            modifier = Modifier.clickable{
                onNavigateToRegister()
            }
        )
    }


}