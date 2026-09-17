import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.email_client_app.data.dto.MailBoxType
import com.example.email_client_app.ui.viewmodel.EmailDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailDetailScreen(
    uid: Long,
    mailboxType: MailBoxType,
    navController: NavController
) {

    val viewModel: EmailDetailViewModel = hiltViewModel()

    LaunchedEffect(uid, mailboxType) {
        viewModel.loadEmail(uid, mailboxType)
    }

    val email = viewModel.email

    Scaffold(

        topBar = {
            TopAppBar(
                title = {
                    Text("Email")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }

    ) { padding ->

        if (email == null) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

        } else {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {

                Text(
                    text = "From: ${email.from}",
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "To: ${email.to}"
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = email.subject,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(16.dp))

                Text(email.body)

                Spacer(Modifier.height(24.dp))

                // Don't show Reply for Sent emails
                if (mailboxType != MailBoxType.SENT) {
                    Button(
                        onClick = {
                            navController.navigate(
                                "reply/${email.uid}"
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Reply")
                    }
                }
            }
        }
    }
}