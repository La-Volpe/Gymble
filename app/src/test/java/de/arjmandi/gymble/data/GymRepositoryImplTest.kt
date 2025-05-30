package de.arjmandi.gymble.data

import androidx.compose.material3.ExposedDropdownMenuBox
import de.arjmandi.gymble.data.dto.GymDtoItem
import de.arjmandi.gymble.data.dto.GymListDto
import de.arjmandi.gymble.data.dto.VibeDto
import de.arjmandi.gymble.data.remote.ApiResult
import de.arjmandi.gymble.data.remote.GymsListApi
import de.arjmandi.gymble.domain.model.Gym
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

class GymRepositoryImplTest {

    private val gymsListApi: GymsListApi = mockk()
    private val gymRepository = GymRepositoryImpl(gymsListApi)

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
    val gymListDto = GymListDto(
        listOf(
            GymDtoItem(
                name = "Geralt’s Gym",
                slogan = "No excuses. Just contracts.",
                specialty = "SwordFit & Potion Conditioning",
                vibe = listOf(
                    VibeDto(emoji = "⚔️", trait = "Intense"),
                    VibeDto(emoji = "🤫", trait = "Silent")
                ),
                quote = "Hmm.",
                imageUrl = "https://res.cloudinary.com/dlgtohpdw/image/upload/v1748305418/2_geralt_e3xmx7.webp"
            ),
            GymDtoItem(
                name = "Yennefer’s Studio",
                slogan = "Power. Poise. And push.",
                specialty = "Mystic Strength & Arcane Yoga",
                vibe = listOf(
                    VibeDto(emoji = "🔮", trait = "Elegant"),
                    VibeDto(emoji = "🧘‍♀️", trait = "Intimidating")
                ),
                quote = "Magic won't fix your form. I will.",
                imageUrl = "https://res.cloudinary.com/dlgtohpdw/image/upload/v1748305414/1_yennefer_t4dur3.webp"
            )
        )
    )


    @Test
    fun getGyms_ReturnsCorrectList_APISuccess() = runTest {
        // Given
        coEvery { gymsListApi.fetchGymList() } returns ApiResult.Success(gymListDto)
        // When
        val result = gymRepository.getGyms()
        // Then
        assertEquals(gyms, result)
    }

    @Test
    fun getGyms_ReturnsError_APIFailure() = runTest {
        // Given
        coEvery { gymsListApi.fetchGymList() } returns ApiResult.HttpError(404, "Not Found")
        // When & Then
        assertThrows(Exception::class.java) {
            runBlocking { gymRepository.getGyms() }
        }
    }
}