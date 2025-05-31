package de.arjmandi.gymble.domain.usecase

import de.arjmandi.gymble.domain.model.Gym
import de.arjmandi.gymble.domain.repository.GymRepository

class GetGymsUseCase(private val repository: GymRepository) {
	suspend operator fun invoke(): List<Gym> {
		return repository.getGyms()
	}
}
