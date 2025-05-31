package de.arjmandi.gymble.data.remote

import de.arjmandi.gymble.data.dto.GymListDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.json.Json

class GymsListApi(private val client: HttpClient) {

	suspend fun fetchGymList(): ApiResult<GymListDto> {
		return try {
			val response: HttpResponse = client.get("https://pastebin.com/raw/k6LBh98f") {
				headers {
					append(HttpHeaders.Accept, "text/plain")
				}
			}

            /*
            val text = response.bodyAsText()
            val data = Json { ignoreUnknownKeys = true }.decodeFromString<GymListDto>(text)
             */
			when (response.status) {
				HttpStatusCode.OK -> {
					val text = response.bodyAsText()
					val data = Json.decodeFromString<GymListDto>(text)
					ApiResult.Success(data)
				}
				HttpStatusCode.TooManyRequests -> ApiResult.HttpError(429, "Rate limit exceeded. Please slow down.")
				HttpStatusCode.NotFound -> ApiResult.HttpError(404, "Requested resource not found.")
				HttpStatusCode.InternalServerError -> ApiResult.HttpError(500, "Internal server error.")
				HttpStatusCode.NotImplemented -> ApiResult.HttpError(501, "Server does not support this feature.")
				HttpStatusCode.BadGateway -> ApiResult.HttpError(502, "Bad gateway error.")
				HttpStatusCode.ServiceUnavailable -> ApiResult.HttpError(503, "Service is temporarily unavailable.")
				HttpStatusCode.GatewayTimeout -> ApiResult.HttpError(504, "Gateway timeout error.")
				else -> ApiResult.HttpError(response.status.value, "Unexpected error: ${response.status.description}")
			}
		} catch (e: Exception) {
			ApiResult.NetworkError(e)
		}
	}

	fun close() {
		client.close()
	}
}
