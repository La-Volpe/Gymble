package de.arjmandi.gymble.domain.usecase

import de.arjmandi.gymble.domain.model.SwipeDirection
import de.arjmandi.gymble.domain.model.SwipeResult
import kotlin.random.Random

class SwipeGymUseCase {
    operator fun invoke(direction: SwipeDirection): SwipeResult {
        return if (direction == SwipeDirection.RIGHT && Random.nextInt(10) == 0) {
            SwipeResult.Match
        } else {
            SwipeResult.NoMatch
        }
    }
}