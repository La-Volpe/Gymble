package de.arjmandi.gymble.domain.model

data class Gym(
    val name: String,
    val slogan: String,
    val specialty: String,
    val vibe: List<String>,
    val quote: String,
    val imageUrl: String = "https://placehold.co/400"
)