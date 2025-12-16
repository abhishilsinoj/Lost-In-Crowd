
package com.uog.expansionappredesign.model

import com.uog.expansionappredesign.R
import kotlin.random.Random

enum class EventType {
    CONCERT, FESTIVAL
}
/**
 * Represents a specific music event (Concert or Festival).
 *
 * @property id Unique identifier for the event.
 * @property name The title of the artist or festival.
 * @property venue The venue name/location.
 * @property price The cost per single ticket.
 * @property imageRes The resource ID for the event pic.
 * @property description A summary of the event details.
 */
data class MusicEvent(
    val id: Int,
    val name: String,
    val venue: String,
    val date: String,
    val imageRes: Int,
    val price: Double,
    val type: EventType,
    val description: String,
    val totalCapacity: Int = Random.nextInt(2000, 5000),
    val ticketsSold: Int = (totalCapacity * Random.nextDouble(0.4, 0.98)).toInt()
) {
    val ticketsRemaining: Int
        get() = totalCapacity - ticketsSold
    val percentSold: Float
        get() = ticketsSold.toFloat() / totalCapacity.toFloat()
}

object EventRepository {
    /**
     * A static data source that acts as the Model in the MVVM architecture.
     * It simulates a database or API by providing a list of all available
     * concerts and festivals.
     */
    val events = listOf(
// CONCERTS
        MusicEvent(1, "Lily Allen", "Manchester Arena", "Jun 19, 2026", R.mipmap.lily, 65.00, EventType.CONCERT, "The West End Girl Tour"),
        MusicEvent(2, "Rick Astley", "The O2 (London)", "Apr 25, 2026", R.mipmap.rick, 50.00, EventType.CONCERT, "The Reflection Tour"),
        MusicEvent(3, "Alex Warren", "The O2 (London)", "Apr 20, 2026", R.mipmap.alex, 35.00, EventType.CONCERT, "Little Orphan Alex Live"),
        MusicEvent(4, "Metallica", "London Stadium", "Jul 5, 2026", R.mipmap.metallica, 120.00, EventType.CONCERT, "M72 World Tour"),
        MusicEvent(5, "Ariana Grande", "The O2 (London)", "Aug 15, 2026", R.mipmap.ariana, 110.00, EventType.CONCERT, "The Eternal Sunshine Tour"),
        MusicEvent(6, "Maroon 5", "Hyde Park", "Jul 3, 2026", R.mipmap.maroon, 90.00, EventType.CONCERT, "BST Hyde Park Headliner"),
        MusicEvent(7, "Biffy Clyro", "Finsbury Park", "Jul 3, 2026", R.mipmap.biffy, 75.00, EventType.CONCERT, "The Futique Tour"),
        MusicEvent(8, "YUNGBLUD", "The O2 (London)", "Apr 24, 2026", R.mipmap.yungblud, 45.00, EventType.CONCERT, "The Idols Tour"),
        MusicEvent(9, "Doja Cat", "The O2 (London)", "May 29, 2026", R.mipmap.doja, 85.00, EventType.CONCERT, "The Tour Ma Vie World Tour"),
        MusicEvent(10, "Hans Zimmer", "The O2 (London)", "Oct 12, 2026", R.mipmap.hans, 95.00, EventType.CONCERT, "A New Dimension"),

// FESTIVALS
        MusicEvent(11, "Download Festival", "Donington Park", "Jun 10–14, 2026", R.mipmap.download, 260.00, EventType.FESTIVAL, "Rock/Metal."),
        MusicEvent(12, "Boomtown", "Matterley Estate", "Aug 12–16, 2026", R.mipmap.boomtown, 275.00, EventType.FESTIVAL, "Theatrical City."),
        MusicEvent(13, "TRNSMT", "Glasgow Green", "Jun 19–21, 2026", R.mipmap.trnsmt, 185.00, EventType.FESTIVAL, "Scotland's biggest."),
        MusicEvent(14, "BST Hyde Park", "Hyde Park", "Jun 27–Jul 12", R.mipmap.bst, 90.00, EventType.FESTIVAL, "London Headliners."),
        MusicEvent(15, "Latitude", "Henham Park", "Jul 23–26, 2026", R.mipmap.latitude, 230.00, EventType.FESTIVAL, "Arts & Music."),
        MusicEvent(16, "Boardmasters", "Newquay", "Aug 5–9, 2026", R.mipmap.boardmasters, 240.00, EventType.FESTIVAL, "Surf & Soul."),
        MusicEvent(17, "Isle of Wight", "Seaclose Park", "Jun 18–21, 2026", R.mipmap.isle, 215.00, EventType.FESTIVAL, "Iconic British festival."),
        MusicEvent(18, "All Points East", "Victoria Park", "Aug 22–30, 2026", R.mipmap.all, 80.00, EventType.FESTIVAL, "East London Alt."),
        MusicEvent(19, "Reading & Leeds", "Reading", "Aug 27–30, 2026", R.mipmap.reading, 285.00, EventType.FESTIVAL, "Rite of Passage."),
        MusicEvent(20, "Creamfields", "Daresbury", "Aug 27–30, 2026", R.mipmap.creamsfield, 250.00, EventType.FESTIVAL, "Electronic Dance.")
    )
    fun getConcerts(): List<MusicEvent> {
        return events.filter { it.type == EventType.CONCERT }
    }

    fun getFestivals(): List<MusicEvent> {
        return events.filter { it.type == EventType.FESTIVAL }
    }

    fun getSpotlightEvents(): List<MusicEvent> {
        return events.filter { it.type == EventType.CONCERT}.shuffled().take(5)
    }
    fun getEventById(id: Int): MusicEvent? {
        return events.find { it.id == id }
    }
}