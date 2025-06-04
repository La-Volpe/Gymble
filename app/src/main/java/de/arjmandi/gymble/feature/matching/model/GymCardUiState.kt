package de.arjmandi.gymble.feature.matching.model

import de.arjmandi.gymble.domain.model.Gym

data class GymCardUiState(
	val imageUrl: String,
	val title: String,
	val subtitle: String,
	val description: String,
	val vibe: List<String>,
	val quote: String
)
fun Gym.toGymCardUiState(): GymCardUiState {
	return GymCardUiState(
		imageUrl = this.imageUrl,
		title = this.name,
		subtitle = this.slogan,
		description = this.specialty,
		vibe = this.vibe,
		quote = this.quote
	)
}