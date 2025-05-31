package de.arjmandi.gymble.data

import de.arjmandi.gymble.data.remote.ApiResult
import de.arjmandi.gymble.data.remote.GymsListApi
import de.arjmandi.gymble.data.remote.toDomain
import de.arjmandi.gymble.domain.model.Gym
import de.arjmandi.gymble.domain.repository.GymRepository

class GymRepositoryImpl(private val gymsListApi: GymsListApi) : GymRepository {

	override suspend fun getGyms(): List<Gym> {
		return when (val result = gymsListApi.fetchGymList()) {
			is ApiResult.Success -> result.data.toDomain()
			is ApiResult.HttpError -> throw Exception(result.message)
			is ApiResult.NetworkError -> throw result.exception
		}
	}
}
