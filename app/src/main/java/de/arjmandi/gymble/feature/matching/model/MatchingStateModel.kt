package de.arjmandi.gymble.feature.matching.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class MatchingStateModel {
	var state by mutableStateOf(MatchingUiState())
		private set

	fun update(newState: MatchingUiState) {
		state = newState
	}
}
