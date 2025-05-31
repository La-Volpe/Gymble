package de.arjmandi.gymble.feature.matching.model

import de.arjmandi.gymble.domain.model.Gym

data class MatchingUiState(
	val gyms: List<Gym> = emptyList(),
	val currentIndex: Int = 0,
	val isLoading: Boolean = false,
	val error: String? = null,
)
