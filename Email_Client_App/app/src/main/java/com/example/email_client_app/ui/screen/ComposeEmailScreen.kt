package com.example.email_client_app.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDialog
import android.app.TimePickerDialog
import androidx.activity.compose.BackHandler
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePickerDialog
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.email_client_app.ui.viewmodel.AuthViewModel
import com.example.email_client_app.ui.viewmodel.ComposeViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeEmailScreen(
    navController: NavController,
    viewModel: ComposeViewModel = hiltViewModel()
){

    var to by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }


    var scheduledAt by remember {
        mutableStateOf<String?>(null)
    }

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    var selectedDate by remember {
        mutableStateOf<LocalDate?>(null)
    }

    var selectedTime by remember {
        mutableStateOf<LocalTime?>(null)
    }

    val context = LocalContext.current

    var showTimePicker by remember {
        mutableStateOf(false)
    }

    val hasChanges = to.isNotBlank() ||
                     subject.isNotBlank() ||
                     body.isNotBlank()

    var showDialog by remember {
        mutableStateOf(false)
    }

    fun handleBack(){
        if(hasChanges){
            showDialog = true
        }else {
            navController.popBackStack()
        }
    }

    BackHandler {
        handleBack()
    }

    if(showDialog){
        AlertDialog(onDismissRequest = {
            showDialog = false
        },
            title = {
                Text("Save Draft?")
            },
            text = {
                Text("Do you want to save this email as a draft?")
            },
            confirmButton = {
                Button(onClick = {
                    showDialog = false

                    viewModel.saveDraft(
                        to = to,
                        subject = subject,
                        body = body,
                        onSuccess = {
                            navController.popBackStack()
                        }
                    )
                }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog = false

                        //Discard Draft
                        navController.popBackStack()
                    }
                ) {
                    Text("No")
                }
            }
        )
    }


    val scope = rememberCoroutineScope()

    if (showDatePicker) {

        val today = LocalDate.now()

        android.app.DatePickerDialog(
            context,
            { _, year, month, day ->

                selectedDate = LocalDate.of(
                    year,
                    month + 1,
                    day
                )

                showDatePicker = false
                showTimePicker = true

            },
            today.year,
            today.monthValue - 1,
            today.dayOfMonth
        ).show()
    }

    if(showTimePicker){

        TimePickerDialog(
            context, { _, hour, minute ->
                selectedTime = LocalTime.of(hour, minute)

                val dateTime = LocalDateTime.of(
                    selectedDate!!, selectedTime!!
                )

                scheduledAt = dateTime.format(
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME
                )

                showTimePicker = false


            },

            12,
            0,
            false

        ).show()
    }



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Compose") },
                navigationIcon = {
                    IconButton( onClick = {
                        handleBack()
                    }
                    ) {
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
                    showDatePicker = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    if (scheduledAt == null) {
                        "Schedule Email"
                    } else {
                        val formatted = LocalDateTime
                            .parse(scheduledAt)
                            .format(
                                DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")
                            )

                        "Scheduled: $formatted"
                    }
                )
            }

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    viewModel.sendEmail(
                        to = to,
                        subject = subject,
                        body = body,
                        scheduledAt = scheduledAt,
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