package com.uog.expansionappredesign.ui.theme.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.uog.expansionappredesign.R
import kotlinx.coroutines.delay

/**
 * Displays the app’s splash screen.the screen shows the lostincrowd logo
 * for about two seconds, then automatically takes the user to the Home screen.
 * A timed effect handles the delay and removes the splash screen
 *
 * from the navigation history,so users can’t navigate back to it.
 * @param navController Handles navigation to the Home screen after the delay.
 * It uses a [LaunchedEffect] to handle the timing and ensures the splash screen
 * is removed from the back stack (`popUpTo`) so users cannot return to it.
 * @param navController Used to navigate to the "home" destination after the delay.
 */
@Composable
fun Opening(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("home") {
            popUpTo("opening") { inclusive = true }
        }
    }

    // The UI Layout
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.mipmap.lostincrowd),
            contentDescription = stringResource(R.string.app_name),
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}