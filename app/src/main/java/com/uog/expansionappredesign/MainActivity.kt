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
import com.uog.expansionappredesign.ui.theme.screens.DetailScreen
import com.uog.expansionappredesign.ui.theme.screens.HelpScreen
import com.uog.expansionappredesign.ui.theme.screens.Opening
import com.uog.expansionappredesign.viewmodel.AppView
import com.uog.expansionappredesign.ui.theme.screens.TicketsScreen

/**
 * The entry point of the Lost In Crowd application.
 *
 * Sets up the [NavHost] for navigation and wraps the entire application
 * in the [LostInCrowdTheme] based on the ViewModel's state.
 */
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
/**
 * Configures the main navigation line for the application.
 *
 * This Composable initializes the [NavHost] and defines the navigation routes:
 * - opening: The splash screen.
 * - home: The main dashboard.
 * - details: The specific event details page (accepts an integer argument).
 * - help: The user guide screen.
 * - tickets: The user's digital wallet.
 *
 * @param viewModel The shared [AppView] instance passed to screens that require state access.
 */
@Composable
fun LostInCrowdApp(viewModel: AppView) {
    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = "opening"
    ){
        composable("opening") {
            Opening(navController = navController)
        }
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