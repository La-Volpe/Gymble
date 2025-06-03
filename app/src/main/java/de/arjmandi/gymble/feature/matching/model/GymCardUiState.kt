package de.arjmandi.gymble.feature.matching.model

data class GymCardUiState(
	val imageUrl: String,
	val title: String,
	val subtitle: String,
	val description: String,
	val vibe: List<String>,
	val quote: String
)