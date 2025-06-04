package de.arjmandi.gymble.feature.matching.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.arjmandi.gymble.domain.model.Gym
import de.arjmandi.gymble.domain.model.SwipeDirection
import de.arjmandi.gymble.domain.model.SwipeResult
import de.arjmandi.gymble.feature.matching.MatchingContext
import de.arjmandi.gymble.feature.matching.model.MatchingUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MatchingViewModel(
	private val context: MatchingContext,
) : ViewModel() {

	private val _uiState = MutableStateFlow<MatchingUiState>(MatchingUiState.LoadingState())
	val uiState: StateFlow<MatchingUiState> = _uiState.asStateFlow()
	private val _matchEvent = MutableStateFlow<SwipeResult>(SwipeResult.NoMatch)
	val matchEvent: StateFlow<SwipeResult> = _matchEvent.asStateFlow()
	private val cachedGyms = MutableStateFlow<List<Gym>>(emptyList())


	fun loadGyms() {
		viewModelScope.launch {
			_uiState.value = MatchingUiState.LoadingState()
			try {
				cachedGyms.value = context.loadGyms()
				_uiState.value = MatchingUiState.LoadedState(cachedGyms.value)
			} catch (e: Exception) {
				_uiState.value = MatchingUiState.ErrorState(e.message ?: "An error occurred")
			}
		}
	}

	fun handleSwipe(direction: SwipeDirection) {
		_matchEvent.value = context.swipe(direction)
	}

	fun restock() {
		//_uiState.value = MatchingUiState.LoadedState(context.loadGyms())
		viewModelScope.launch {
			_uiState.update { it ->
				if(cachedGyms.value.isEmpty()) {
					MatchingUiState.LoadedState(context.loadGyms())
				} else {
					MatchingUiState.LoadedState(cachedGyms.value.shuffled())
				}
			}
		}
	}
}
