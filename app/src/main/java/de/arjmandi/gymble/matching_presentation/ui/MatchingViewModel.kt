package de.arjmandi.gymble.matching_presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.arjmandi.gymble.domain.model.SwipeDirection
import de.arjmandi.gymble.matching_presentation.MatchingContext
import de.arjmandi.gymble.matching_presentation.model.MatchingEvent
import de.arjmandi.gymble.matching_presentation.model.MatchingStateModel
import de.arjmandi.gymble.matching_presentation.model.MatchingUiState
import kotlinx.coroutines.launch

class MatchingViewModel(
    private val context: MatchingContext
) : ViewModel() {

    val stateModel = MatchingStateModel()

    fun onEvent(event: MatchingEvent) {
        when (event) {
            is MatchingEvent.LoadGyms -> loadGyms()
            is MatchingEvent.Swipe -> handleSwipe(event.direction)
        }
    }

    private fun loadGyms() {
        viewModelScope.launch {
            stateModel.update(stateModel.state.copy(isLoading = true, error = null))
            try {
                val gyms = context.loadGyms()
                stateModel.update(MatchingUiState(gyms = gyms))
            } catch (e: Exception) {
                stateModel.update(stateModel.state.copy(isLoading = false, error = e.message))
            }
        }
    }

    private fun handleSwipe(direction: SwipeDirection) {
        val currentIndex = stateModel.state.currentIndex
        if (currentIndex >= stateModel.state.gyms.size - 1) return

        context.swipe(direction)
        stateModel.update(stateModel.state.copy(currentIndex = currentIndex + 1))
    }
}