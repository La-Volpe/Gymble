package de.arjmandi.gymble.data.remote

import de.arjmandi.gymble.data.dto.GymListDto
import de.arjmandi.gymble.domain.model.Gym

fun GymListDto.toDomain(): List<Gym> {
    return this.dto.map {
            gymDtoItem ->
        Gym(
            name = gymDtoItem.name,
            slogan = gymDtoItem.slogan,
            specialty = gymDtoItem.specialty,
            vibe = gymDtoItem.vibe.map { it.toString() },
            quote = gymDtoItem.quote,
            imageUrl = gymDtoItem.imageUrl
        )
    }

}