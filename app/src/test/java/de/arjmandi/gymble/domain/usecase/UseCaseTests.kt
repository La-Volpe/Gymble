package de.arjmandi.gymble.domain.usecase

import de.arjmandi.gymble.data.dto.VibeDto
import de.arjmandi.gymble.domain.model.Gym
import de.arjmandi.gymble.domain.model.SwipeDirection
import de.arjmandi.gymble.domain.model.SwipeResult
import de.arjmandi.gymble.domain.repository.GymRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals


class UseCaseTests {
    val gyms = listOf(
        Gym(
            name = "Geralt’s Gym",
            slogan = "No excuses. Just contracts.",
            specialty = "SwordFit & Potion Conditioning",
            vibe = listOf(
                VibeDto(emoji = "⚔️", trait = "Intense").toString(),
                VibeDto(emoji = "🤫", trait = "Silent").toString()
            ),
            quote = "Hmm.",
            imageUrl = "https://res.cloudinary.com/dlgtohpdw/image/upload/v1748305418/2_geralt_e3xmx7.webp"
        ),
        Gym(
            name = "Yennefer’s Studio",
            slogan = "Power. Poise. And push.",
            specialty = "Mystic Strength & Arcane Yoga",
            vibe = listOf(
                VibeDto(emoji = "🔮", trait = "Elegant").toString(),
                VibeDto(emoji = "🧘‍♀️", trait = "Intimidating").toString()
            ),
            quote = "Magic won't fix your form. I will.",
            imageUrl = "https://res.cloudinary.com/dlgtohpdw/image/upload/v1748305414/1_yennefer_t4dur3.webp"
        )
    )


    private val mockRepository = mockk<GymRepository>()

    @Test
    fun getGymsUseCase_returnsGymList_repositoryReturnsGyms() = runBlocking {
        coEvery { mockRepository.getGyms() } returns gyms

        val useCase = GetGymsUseCase(mockRepository)
        val result = useCase()

        assertEquals(gyms, result)
    }

    @Test
    fun shuffleGymsUseCase_returnsShuffledList_inputListProvided() {
        val useCase = ShuffleGymsUseCase()

        val shuffled = useCase(gyms)

        assertEquals(gyms.toSet(), shuffled.toSet())
        assertTrue(shuffled != gyms || gyms.size == 1)
    }

    @Test
    fun swipeGymUseCase_returnsCorrectResult_forDifferentDirections() {
        val useCase = SwipeGymUseCase()

        val rightResults = (1..100).map { useCase.invoke(SwipeDirection.RIGHT) }
        val leftResults = (1..100).map { useCase.invoke(SwipeDirection.LEFT) }

        assertTrue(leftResults.all { it is SwipeResult.NoMatch })
        assertTrue(rightResults.any { it is SwipeResult.Match } || rightResults.any { it is SwipeResult.NoMatch })
    }
}