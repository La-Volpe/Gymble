package de.arjmandi.gymble.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VibeDto(
    @SerialName("emoji")
    val emoji: String = "",
    @SerialName("trait")
    val trait: String = ""
){
    override fun toString(): String {
        return "$emoji $trait"
    }
}