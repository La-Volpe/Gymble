package de.arjmandi.gymble.matching_presentation.model

import de.arjmandi.gymble.domain.model.SwipeDirection

sealed class MatchingEvent {
    data object LoadGyms : MatchingEvent()
    data class Swipe(val direction: SwipeDirection) : MatchingEvent()
}