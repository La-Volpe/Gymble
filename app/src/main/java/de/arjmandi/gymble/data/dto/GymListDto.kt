package de.arjmandi.gymble.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class GymListDto(
    @SerialName("items")
    val dto: List<GymDtoItem>
)