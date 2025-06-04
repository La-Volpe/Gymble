package de.arjmandi.gymble.feature.matching.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.arjmandi.gymble.domain.model.Gym
import de.arjmandi.gymble.domain.model.SwipeDirection
import de.arjmandi.gymble.domain.model.SwipeResult
import de.arjmandi.gymble.feature.matching.MatchingContext
import de.arjmandi.gymble.feature.matching.model.GymCardUiState
import de.arjmandi.gymble.feature.matching.model.MatchedResultUiState
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
	private val _matchedGymUiState = MutableStateFlow<MatchedResultUiState>(MatchedResultUiState.NoMatchState)
	val matchedGymUiState: StateFlow<MatchedResultUiState> = _matchedGymUiState.asStateFlow()

	private val cachedGyms = MutableStateFlow<List<Gym>>(emptyList())
	private val _gymsDoNotLoveYouCounter = MutableStateFlow(0)

	val onDismissMatchedGymOverlay = { _matchedGymUiState.value = MatchedResultUiState.NoMatchState }

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

	fun handleSwipe(direction: SwipeDirection, swipedGym: GymCardUiState) {
		val result = context.swipe(direction)
		_matchEvent.value = result
		when (result) {
			is SwipeResult.Match -> {
				_matchedGymUiState.value = MatchedResultUiState.MatchedState(swipedGym.copy(isMatched = true))
				Log.d("TALA", "Matched with gym: ${swipedGym.title}")
			}
			is SwipeResult.NoMatch -> {
				_gymsDoNotLoveYouCounter.update { it + 1 }
			}
		}
	}

	fun restock() {
		// _uiState.value = MatchingUiState.LoadedState(context.loadGyms())
		viewModelScope.launch {
			_uiState.update { it ->
				if (cachedGyms.value.isEmpty()) {
					MatchingUiState.LoadedState(context.loadGyms())
				} else {
					MatchingUiState.LoadedState(cachedGyms.value.shuffled())
				}
			}
		}
	}
}
