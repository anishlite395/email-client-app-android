package com.example.email_client_app.ui.navigation

import EmailDetailScreen
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

        composable(Routes.SCHEDULED){
            MailboxScreen(
                mailboxType = MailBoxType.SCHEDULED,
                navController = navController
            )
        }

        composable(
            route = "${Routes.EMAIL_DETAIL}/{mailboxType}/{uid}",
            arguments = listOf(
                navArgument("mailboxType"){
                    type = NavType.StringType
                },
                navArgument("uid"){
                    type = NavType.LongType
                }
            )
        ){
                backStackEntry ->
            val mailboxType =
                backStackEntry.arguments
                    ?.getString("mailboxType")
                    ?.let {
                        MailBoxType.valueOf(it)
                    }
                    ?: MailBoxType.INBOX

            val uid = backStackEntry.arguments?.getLong("uid") ?: 0L

            EmailDetailScreen(
                uid = uid,
                mailboxType = mailboxType,
                navController
            )
        }

        composable(Routes.COMPOSE) {
            ComposeEmailScreen(navController)
        }

        composable(route = Routes.COMPOSE_DRAFT,
            arguments = listOf(
                navArgument("uid"){
                    type = NavType.LongType
                }
            )){ backStackEntry ->

                val uid = backStackEntry.arguments?.getLong("uid")

                ComposeEmailScreen(
                    navController = navController,
                    uid = uid
                )
        }

        composable(route = Routes.REPLY,
            arguments = listOf(
                navArgument("uid"){
                    type = NavType.LongType
                }
            )
        ) {
            backStackEntry ->
                val uid = backStackEntry.arguments?.getLong("uid")

                ComposeEmailScreen(
                    navController = navController,
                    replyUid = uid
                )
        }
    }
}
