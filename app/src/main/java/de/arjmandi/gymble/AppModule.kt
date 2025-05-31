package de.arjmandi.gymble

import de.arjmandi.gymble.data.GymRepositoryImpl
import de.arjmandi.gymble.data.remote.GymsListApi
import de.arjmandi.gymble.domain.repository.GymRepository
import de.arjmandi.gymble.domain.usecase.GetGymsUseCase
import de.arjmandi.gymble.domain.usecase.ShuffleGymsUseCase
import de.arjmandi.gymble.domain.usecase.SwipeGymUseCase
import de.arjmandi.gymble.feature.matching.MatchingContext
import de.arjmandi.gymble.feature.matching.ui.MatchingViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val domainModule = module {
	factory { GetGymsUseCase(get<GymRepository>()) }
	factory { ShuffleGymsUseCase() }
	factory { SwipeGymUseCase() }
}

val dataModule = module {
	single<HttpClient> {
		HttpClient(CIO) {
			install(ContentNegotiation) {
				json(
					Json {
						ignoreUnknownKeys = true
					},
				)
			}
			install(Logging) {
				logger = Logger.DEFAULT
				level = LogLevel.HEADERS
			}
		}
	}
	single<GymsListApi> { GymsListApi(get<HttpClient>()) }
	single<GymRepository> { GymRepositoryImpl(get<GymsListApi>()) }
}

val matchingModule = module {
	single<MatchingContext> {
		MatchingContext(
			get<GetGymsUseCase>(),
			get<ShuffleGymsUseCase>(),
			get<SwipeGymUseCase>(),
		)
	}
	viewModel<MatchingViewModel> { MatchingViewModel(get<MatchingContext>()) }
}
