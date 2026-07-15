package com.example.email_client_app.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.email_client_app.ui.viewmodel.AuthViewModel
import com.example.email_client_app.ui.viewmodel.ComposeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeEmailScreen(
    navController: NavController,
    viewModel: ComposeViewModel = hiltViewModel()
){
    var to by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Compose") },
                navigationIcon = {
                    IconButton( onClick = { navController.popBackStack()}) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ){
        padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TextField(
                value = to,
                onValueChange = { to = it},
                label = { Text("to") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            TextField(
                value = subject,
                onValueChange = { subject = it},
                label = { Text("Subject")},
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            TextField(
                value = body,
                onValueChange = { body = it },
                label = { Text("Message") },
                modifier = Modifier.fillMaxWidth().height(250.dp),
                maxLines = 10
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    viewModel.sendEmail(
                        to = to,
                        subject = subject,
                        body = body,
                        onSuccess = {

                            //Tell the previous screen that an email was sent
                            navController.previousBackStackEntry
                                ?.savedStateHandle
                                ?.set("email_sent",true)

                            // Go back to Inbox
                            navController.popBackStack()
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Send")
            }
        }

    }



}