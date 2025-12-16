package com.uog.expansionappredesign.ui.theme.screens
import androidx.compose.foundation.Image
import com.uog.expansionappredesign.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uog.expansionappredesign.model.EventRepository
import com.uog.expansionappredesign.model.MusicEvent
import com.uog.expansionappredesign.ui.theme.purple
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

/**
 * The primary dashboard of the application.
 *
 * This screen serves as the main entry point after the splash screen. It features:
 * 1: A top bar with theme toggle and help navigation.
 * 2: A "Spotlight" auto-scrolling carousel for featured events.
 * 3: Horizontal scrolling lists (`LazyRow`) for "Upcoming Concerts"
 *   and "Major Festivals".
 * 4: A floating action button to access the user's ticket wallet.
 *
 * @param onEventClick  to go back to the Detail Screen
 * when an event card is tapped.
 * @param onHelpClick callback to go back to to the Help Screen.
 * @param onToggleTheme to switch between Dark and Light modes.
 * @param onTicketsClick to go to the Tickets Screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onEventClick: (Int) -> Unit,
    onHelpClick: () -> Unit,
    onToggleTheme: () -> Unit,
    onTicketsClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = purple
                ),
                actions = {
                    IconButton(onClick = onToggleTheme) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = stringResource(R.string.theme),
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                    IconButton(onClick = onHelpClick) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = stringResource(R.string.help),
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onTicketsClick,
                containerColor = purple,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Filled.ConfirmationNumber,
                    contentDescription = stringResource(R.string.my_tickets)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(MaterialTheme.colorScheme.background)
        ) {

            SpotlightSection(
                events = EventRepository.getSpotlightEvents(),
                onEventClick = onEventClick
            )

            Spacer(modifier = Modifier.height(24.dp))

            SectionTitle(title = stringResource(R.string.upcoming_concerts))

            EventRow(
                events = EventRepository.getConcerts(),
                onEventClick = onEventClick
            )

            Spacer(modifier = Modifier.height(24.dp))

            SectionTitle(title = stringResource(R.string.major_festivals))

            EventRow(
                events = EventRepository.getFestivals(),
                onEventClick = onEventClick
            )

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}
/**
 * A spotlight that auto-scrolls through featured events.
 * It uses a [HorizontalPager] combined with a [LaunchedEffect] to automatically
 * advance the slide every 3 seconds.
 * @param events List of [MusicEvent] objects to display in the spotlight.
 * @param onEventClick Callback when a specific spotlight card is clicked.
 */
@Composable
fun SpotlightSection(
    events: List<MusicEvent>,
    onEventClick: (Int) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { events.size })

    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(3000)
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % events.size)
        }
    }

    Column {
        Text(
            text = stringResource(R.string.featured_events),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(16.dp)
        )

        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 24.dp),
            pageSpacing = 12.dp
        ) { page ->
            SpotlightCard(
                event = events[page],
                onClick = { onEventClick(events[page].id) }
            )
        }
    }
}

/**
 * A large, prominent card used inside the [SpotlightSection].
 * Displays a high-quality background image with a gradient
 * overlay for text readability.
 */
@Composable
fun SpotlightCard(
    event: MusicEvent,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clickable { onClick() }
    ) {
        Box {
            Image(
                painter = painterResource(id = event.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color.Black),
                            startY = 100f
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            ) {
                Text(
                    text = event.name,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = event.venue,
                    color = Color.LightGray,
                    fontSize = 12.sp
                )
            }
        }
    }
}
/**
 * A simple helper component to render standardized section headers.
 */
@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.secondary,
        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
    )
}
/**
 * A horizontally scrolling list (`LazyRow`) for displaying
 * a collection of events.
 * @param events The list of events to render.
 * @param onEventClick Callback invoked when an item in the row is tapped.
 */
@Composable
fun EventRow(
    events: List<MusicEvent>,
    onEventClick: (Int) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(events) { event ->
            EventCard(
                event = event,
                onEventClick = onEventClick
            )
        }
    }
}
/**
 * A standard sized card component for displaying event details in a list.
 * Shows the event image, name, price, and date.
 */
@Composable
fun EventCard(
    event: MusicEvent,
    onEventClick: (Int) -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .width(135.dp)
            .height(175.dp)
            .clickable { onEventClick(event.id) }
    ) {
        Column {
            Image(
                painter = painterResource(id = event.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(105.dp)
                    .fillMaxWidth()
            )

            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = event.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = stringResource(R.string.price, event.price.toInt()),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = event.date,
                    fontSize = 10.sp,
                    maxLines = 1,
                    color = Color.Gray
                )
            }
        }
    }
}