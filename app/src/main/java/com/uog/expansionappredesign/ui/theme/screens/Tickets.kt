package com.uog.expansionappredesign.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uog.expansionappredesign.model.MusicEvent
import com.uog.expansionappredesign.viewmodel.AppView
import androidx.compose.ui.res.stringResource
import com.uog.expansionappredesign.R

/**
 * The user's digital wallet screen displaying purchased tickets.
 * This screen observes the [viewModel.myTickets] state flow to render the UI:
 * Empty StateIf the list is empty, a centered "No Tickets" message is shown.
 * List State If tickets exist, they are displayed in a vertical [LazyColumn].
 * It also provides the functionality to delete tickets directly from the list.
 * @param viewModel The shared [AppView] to observe the ticket list and handle deletion logic.
 * @param onBackClick Lambda callback to navigate back to the previous screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketsScreen(viewModel: AppView, onBackClick: () -> Unit) {
    val myTickets by viewModel.myTickets.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.my_tickets_wallet)) },
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
    ) { innerPadding -> Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)
    ) {
        if (myTickets.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.no_tickets),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(myTickets) { ticket ->
                    TicketItem(
                        event = ticket,
                        onDelete = { viewModel.removeTicket(ticket) }
                    )
                }
            }
        }
    }
    }
}
/**
 * A reusable list item component representing a single purchased ticket.
 * Renders a [Card] containing the event image, details (name, date, price),
 * and a delete button to remove the ticket from the wallet.
 * @param event The [MusicEvent] data object containing display details.
 * @param onDelete A callback triggered when the delete icon is tapped.
 */
@Composable
fun TicketItem(event: MusicEvent, onDelete: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = event.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(100.dp)
                    .fillMaxHeight()
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp)
            ) {
                Text(
                    text = event.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = event.date,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = stringResource(R.string.price, event.price.toInt()),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(R.string.delete_ticket),
                    tint = Color.Red
                )
            }
        }
    }
}