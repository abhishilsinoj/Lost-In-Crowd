package com.uog.expansionappredesign.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uog.expansionappredesign.model.MusicEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
/**
 * Main ViewModel for the Lost In Crowd app.
 * This ViewModel keeps track of the app’s overall UI state and serves as the one
 * reliable source of truth. It survives configuration changes (like screen rotation)
 * and handles key logic such as:
 * 1: Switching between dark and light themes
 * 2: Processing ticket purchases with realistic, simulated delays
 * 3: Managing the user’s digital wallet (“My Tickets”)
 *
 * @property isDarkTheme Public observable state for the application's visual theme.
 * @property myTickets Public observable list of tickets currently in the user's wallet.
 * @property isProcessing Indicates if a background operation
 * is currently running, like buying a ticket
 */
class AppView : ViewModel() {
    // --- Theme State ---
    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()
    // --- Ticket Wallet State ---
    private val _myTickets = MutableStateFlow<List<MusicEvent>>(emptyList())
    val myTickets: StateFlow<List<MusicEvent>> = _myTickets.asStateFlow()
    // --- Loading/Processing State ---
    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> = _isProcessing.asStateFlow()
    /**
     * Toggles the application theme between Light and Dark modes.
     */
    fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
    }
    /**
     * Simulates a network transaction to purchase a ticket.
     *
     * This function launches a coroutine on the [viewModelScope]
     * to prevent blocking the main thread.
     *
     * It waits about two seconds to mimic a real API call,
     * then adds the purchased tickets to the user’s wallet.
     *
     * @param event The [MusicEvent] the user wishes to attend.
     * @param count The number of tickets to purchase.
     * @param onComplete A callback function to execute when the transaction is finished.
     */
    fun buyTicket(event: MusicEvent, count: Int, onComplete: () -> Unit) {
        viewModelScope.launch {
            _isProcessing.value = true
            delay(2000)
            val currentList = _myTickets.value.toMutableList()
            repeat(count) {
                currentList.add(event)
            }
            _myTickets.value = currentList
            _isProcessing.value = false
            onComplete()
        }
    }
    /**
     * Removes a specific ticket from the user's wallet.
     *
     * @param event The [MusicEvent] object to be removed from the list.
     */
    fun removeTicket(event: MusicEvent) {
        val currentList = _myTickets.value.toMutableList()
        currentList.remove(event)
        _myTickets.value = currentList
    }
}