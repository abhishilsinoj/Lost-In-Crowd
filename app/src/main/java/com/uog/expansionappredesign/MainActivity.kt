package com.uog.expansionappredesign
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.uog.expansionappredesign.ui.theme.LostInCrowdTheme
import com.uog.expansionappredesign.ui.theme.screens.HomeScreen
import com.uog.lostincrowd.ui.theme.screens.DetailScreen
import com.uog.expansionappredesign.ui.theme.screens.HelpScreen
import com.uog.expansionappredesign.viewmodel.AppView
import com.uog.lostincrowd.ui.theme.screens.TicketsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: AppView = viewModel()
            val isDark by viewModel.isDarkTheme.collectAsState()

            LostInCrowdTheme(darkTheme = isDark) {
                LostInCrowdApp(viewModel)
            }
        }
    }
}
@Composable
fun LostInCrowdApp(viewModel: AppView) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onEventClick = { eventId ->
                    navController.navigate("detail/$eventId")
                },
                onHelpClick = {
                    navController.navigate("help")
                },
                onToggleTheme = {
                    viewModel.toggleTheme()
                },
                onTicketsClick = {
                    navController.navigate("tickets")
                }
            )
        }
        composable(
            route = "detail/{eventId}",
            arguments = listOf(
                navArgument("eventId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getInt("eventId") ?: 0
            DetailScreen(
                eventId = eventId,
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onBuySuccess = {
                    navController.popBackStack()
                }
            )
        }
        composable("help") {
            HelpScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable("tickets") {
            TicketsScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}