package com.uog.expansionappredesign.ui.theme.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uog.expansionappredesign.model.EventRepository
import com.uog.expansionappredesign.ui.theme.Green
import com.uog.expansionappredesign.ui.theme.Red
import com.uog.expansionappredesign.viewmodel.AppView
import androidx.compose.ui.res.stringResource
import com.uog.expansionappredesign.R

/**
 * Displays detailed information about a specific music event selected by the user.
 *
 * This screen serves as the main "Product Page" for the application. It provides:
 * - **Visual Details:** A large header image and event description.
 * - **Availability Logic:** A dynamic [LinearProgressIndicator] that changes color (Green/Red) based on the percentage of tickets sold.
 * - **Interactive Purchasing:** A quantity selector that updates the total price in real-time.
 * - **Asynchronous Buying:** A "Buy" button that triggers a background network simulation in the [viewModel], showing a loading spinner while processing.
 *
 * @param eventId The unique identifier of the event to display. Used to retrieve data from the [EventRepository].
 * @param viewModel The shared [AppView] ViewModel used to observe loading states (`isProcessing`) and trigger the ticket purchase logic.
 * @param onBackClick Lambda callback invoked when the user taps the back arrow in the top app bar.
 * @param onBuySuccess Lambda callback invoked after a successful ticket purchase transaction, typically navigating the user back or to their wallet.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    eventId: Int,
    viewModel: AppView,
    onBackClick: () -> Unit,
    onBuySuccess: () -> Unit
) {
    val event = EventRepository.getEventById(eventId)
    val isProcessing by viewModel.isProcessing.collectAsState()
    var quantity by remember { mutableIntStateOf(1) }

    if (event == null) {
        onBackClick()
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = event.name) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            Image(
                painter = painterResource(id = event.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = event.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.price, event.price.toInt()),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = stringResource(R.string.venue_date, event.venue, event.date),
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(R.string.ticket_availability),
                fontWeight = FontWeight.Bold
            )

            val percent = event.percentSold
            val statusColor = if (percent > 0.9f) Red else Green

            LinearProgressIndicator(
                progress = { percent },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(RoundedCornerShape(50)),
                color = statusColor,
                trackColor = Color.LightGray
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val statusText = if (percent > 0.9f) {
                    stringResource(R.string.almost_sold_out)
                } else {
                    stringResource(R.string.selling_fast)
                }

                Text(
                    text = statusText,
                    color = statusColor,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "${event.ticketsRemaining} ${stringResource(R.string.left)}",
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.quantity),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Row(verticalAlignment = Alignment.CenterVertically) {

                    FilledIconButton(
                        onClick = { if (quantity > 1) quantity-- },
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Remove,
                            contentDescription = stringResource(R.string.remove)
                        )
                    }

                    Text(
                        text = "$quantity",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )

                    FilledIconButton(
                        onClick = { quantity++ },
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = stringResource(R.string.add),
                            tint = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.buyTicket(event,quantity) { onBuySuccess() }
                },
                enabled = !isProcessing,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                if (isProcessing) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = stringResource(R.string.processing))
                } else {
                    val total = (event.price * quantity).toInt()
                    Text(
                        text = stringResource(R.string.buy_button, quantity, total),
                        fontSize = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.about),
                style = MaterialTheme.typography.titleMedium
            )

            Text(text = event.description)
        }
    }
}