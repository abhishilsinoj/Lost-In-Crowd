package com.uog.expansionappredesign.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uog.expansionappredesign.model.MusicEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppView : ViewModel() {
    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    private val _myTickets = MutableStateFlow<List<MusicEvent>>(emptyList())
    val myTickets: StateFlow<List<MusicEvent>> = _myTickets.asStateFlow()

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> = _isProcessing.asStateFlow()

    fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
    }
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
    fun removeTicket(event: MusicEvent) {
        val currentList = _myTickets.value.toMutableList()
        currentList.remove(event)
        _myTickets.value = currentList
    }
}