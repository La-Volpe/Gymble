package de.arjmandi.gymble.feature.matching.model

sealed class MatchedResultUiState {
	data class MatchedState(
		val gym: GymCardUiState,
	) : MatchedResultUiState()

	data class ErrorState(
		val error: String,
	) : MatchedResultUiState()

	data object NoMatchState : MatchedResultUiState()
}
