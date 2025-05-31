package de.arjmandi.gymble.domain.repository

import de.arjmandi.gymble.domain.model.Gym

interface GymRepository {
	suspend fun getGyms(): List<Gym>
}
