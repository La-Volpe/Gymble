package de.arjmandi.gymble.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GymDtoItem(
	@SerialName("imageUrl")
	val imageUrl: String = "",
	@SerialName("name")
	val name: String = "",
	@SerialName("quote")
	val quote: String = "",
	@SerialName("slogan")
	val slogan: String = "",
	@SerialName("specialty")
	val specialty: String = "",
	@SerialName("vibe")
	val vibe: List<VibeDto> = listOf(),
)
