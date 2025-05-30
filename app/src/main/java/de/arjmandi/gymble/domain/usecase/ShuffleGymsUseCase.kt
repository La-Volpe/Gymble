package de.arjmandi.gymble.domain.usecase

import de.arjmandi.gymble.domain.model.Gym

class ShuffleGymsUseCase {
    operator fun invoke(gyms: List<Gym>): List<Gym> {
        return gyms.shuffled()
    }
}