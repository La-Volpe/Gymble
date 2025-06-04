package de.arjmandi.gymble.feature.matching.model

import de.arjmandi.gymble.domain.model.Gym


sealed interface MatchingUiState{
	data class LoadingState(
		val isLoading: Boolean = true
	) : MatchingUiState

	data class LoadedState(
		val gyms: List<Gym> = emptyList(),
	) : MatchingUiState

	data class ErrorState(
		val error: String
	) : MatchingUiState
}
