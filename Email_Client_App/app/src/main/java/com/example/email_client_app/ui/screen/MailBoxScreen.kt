package com.example.email_client_app.ui.screen

import EmailItem
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.email_client_app.data.dto.MailBoxType
import com.example.email_client_app.data.remote.Routes
import com.example.email_client_app.ui.viewmodel.MailboxViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MailboxScreen(
    mailboxType: MailBoxType,
    navController: NavController,
) {

    val viewModel: MailboxViewModel = hiltViewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    val selectedEmails = remember { mutableStateListOf<Long>() }

    val selectedItems = remember {
        mutableStateListOf<Long>()
    }

    //Add this
    val selectedDrafts = remember { mutableStateListOf<Long>() }

    LaunchedEffect(mailboxType) {
        viewModel.loadMailbox(mailboxType)
    }

    LaunchedEffect(Unit) {
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow("email_sent", false)
            ?.collect { sent ->
                if (sent) {
                    snackbarHostState.showSnackbar("Email Sent")

                    //Reset the flag so that it does'nt show again
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("email_sent", false)


                    viewModel.loadMailbox(mailboxType)
                }
            }
    }

    val drawerState =
        rememberDrawerState(DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(

        drawerState = drawerState,

        drawerContent = {

            ModalDrawerSheet(
                modifier = Modifier.width(260.dp)
            ) {

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Mail App",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                NavigationDrawerItem(
                    label = { Text("Inbox") },
                    selected = mailboxType == MailBoxType.INBOX,
                    onClick = {
                        navController.navigate(Routes.INBOX)
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Sent") },
                    selected = mailboxType == MailBoxType.SENT,
                    onClick = {
                        navController.navigate(Routes.SENT)
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Drafts") },
                    selected = mailboxType == MailBoxType.DRAFTS,
                    onClick = {
                        navController.navigate(Routes.DRAFTS)
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Scheduled") },
                    selected = mailboxType == MailBoxType.SCHEDULED,
                    onClick = {
                        navController.navigate(Routes.SCHEDULED)
                    }
                )








                Spacer(Modifier.weight(1f))

                HorizontalDivider()

                NavigationDrawerItem(
                    icon = {
                        Icon(
                            Icons.Default.Logout,
                            contentDescription = null,
                            tint = Color.Red
                        )
                    },
                    label = {
                        Text(
                            "Logout",
                            color = Color.Red
                        )
                    },
                    selected = false,
                    onClick = {
                        viewModel.logout {

                            navController.navigate(
                                Routes.LOGIN
                            ) {
                                popUpTo(0) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                )
            }
        }

    ) {

        Scaffold(

            snackbarHost = {
                SnackbarHost(snackbarHostState)
            },

            topBar = {

                TopAppBar(

                    title = {
                        if (selectedItems.isEmpty()) {
                            Text(
                                mailboxType.name.lowercase()
                                    .replaceFirstChar {
                                        it.uppercase()
                                    }
                            )
                        } else {
                            Text("${selectedItems.size} selected")
                        }
                    },

                    navigationIcon = {

                        if (selectedItems.isNotEmpty()) {
                                //cancel selection
                            IconButton(
                                onClick = {
                                    selectedItems.clear()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Cancel selection"
                                )
                            }
                        } else {

                            IconButton(
                                onClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }
                            ) {
                                Icon(
                                    Icons.Default.Menu,
                                    contentDescription = "Open Menu"
                                )
                            }
                        }
                    },
                    actions = {
                        if (selectedItems.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    viewModel.deleteSelectedEmails(
                                        ids = selectedItems.toList(),
                                        mailBoxType = mailboxType
                                    ) {
                                        selectedItems.clear()
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete Emails"
                                )
                            }
                        }
                    }
                )

            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Routes.COMPOSE)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Compose"
                    )
                }
            }

        ) { padding ->
            if (viewModel.emails.isEmpty()) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = when (mailboxType) {
                            MailBoxType.INBOX -> "Your inbox is empty"
                            MailBoxType.SENT -> "No sent emails"
                            MailBoxType.DRAFTS -> "No drafts"
                            MailBoxType.SCHEDULED -> "No scheduled emails"
                        },
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }

            } else {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {

                    items(viewModel.emails) { email ->

                        EmailItem(

                            sender = if (
                                mailboxType == MailBoxType.SENT
                            )
                                email.to
                            else
                                email.from,

                            subject = email.subject,

                            time = email.sentDate,

                            onClick = {
                                if (selectedItems.isNotEmpty()) {
                                    if (selectedItems.contains(email.uid)) {
                                        selectedItems.remove(email.uid)
                                    } else {
                                        selectedItems.add(email.uid)
                                    }
                                } else {
                                    if (mailboxType == MailBoxType.DRAFTS) {
                                        navController.navigate(
                                            "compose/${email.uid}"
                                        )
                                    } else {
                                        navController.navigate(
                                            "${Routes.EMAIL_DETAIL}/${mailboxType.name}/${email.uid}"
                                        )
                                    }
                                }

                            },
                            onLongClick = {
                                if (!selectedItems.contains(email.uid)) {
                                    selectedItems.add(email.uid)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}