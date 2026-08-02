package com.example.email_client_app.ui.navigation

import LoginScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.email_client_app.data.dto.MailBoxType
import com.example.email_client_app.data.remote.Routes
import com.example.email_client_app.ui.screen.ComposeEmailScreen
import com.example.email_client_app.ui.screen.EmailDetailScreen
import com.example.email_client_app.ui.screen.MailboxScreen
import com.example.email_client_app.ui.screen.RegisterScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(){

    val navController = androidx.navigation.compose.rememberNavController()

    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = Routes.REGISTER
    ){
        composable(Routes.REGISTER) {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.navigate(Routes.LOGIN)
                }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Routes.REGISTER)
                },

                onLoginSuccess = {email ->
                    navController.navigate(Routes.INBOX){
                        popUpTo(Routes.LOGIN){
                            inclusive = true
                        }
                    }
                }
            )

        }

        composable(Routes.INBOX) {

            MailboxScreen(
                mailboxType = MailBoxType.INBOX,
                navController = navController
            )
        }

        composable(Routes.SENT) {
            MailboxScreen(
                mailboxType = MailBoxType.SENT,
                navController = navController
            )
        }

        composable(Routes.DRAFTS) {
            MailboxScreen(
                mailboxType = MailBoxType.DRAFTS,
                navController = navController
            )

        }

        composable(
            route = "${Routes.EMAIL_DETAIL}/{uid}",
            arguments = listOf(
                navArgument("uid"){
                    type = NavType.LongType
                }
            )
        ){
            val uid = it.arguments?.getLong("uid") ?: 0L

            EmailDetailScreen(
                uid = uid,
                navController
            )
        }

        composable(Routes.COMPOSE) {
            ComposeEmailScreen(navController)
        }
    }
}
