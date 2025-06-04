package de.arjmandi.gymble.feature.matching

import de.arjmandi.gymble.domain.model.Gym
import de.arjmandi.gymble.domain.model.SwipeDirection
import de.arjmandi.gymble.domain.usecase.GetGymsUseCase
import de.arjmandi.gymble.domain.usecase.ShuffleGymsUseCase
import de.arjmandi.gymble.domain.usecase.SwipeGymUseCase

class MatchingContext(
	private val getGyms: GetGymsUseCase,
	private val shuffleGyms: ShuffleGymsUseCase,
	private val swipeGym: SwipeGymUseCase,
) {
	suspend fun loadGyms(): List<Gym> = shuffleGyms(getGyms())
	fun swipe(direction: SwipeDirection) = swipeGym(direction)
}
