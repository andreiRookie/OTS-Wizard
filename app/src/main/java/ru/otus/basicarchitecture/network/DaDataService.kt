package ru.otus.basicarchitecture.network

import retrofit2.http.Body
import retrofit2.http.POST

interface DaDataService {

    @POST(value = "suggestions/api/4_1/rs/suggest/address")
    suspend fun getAddressSuggestions(
        @Body query: DaDataQuery
    ): DaDataResponse

}