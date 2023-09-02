package ru.otus.basicarchitecture.network

import com.squareup.moshi.Json

data class DaDataResponse(
    @field:Json(name = "suggestions")
    val suggestions: List<SuggestedAddress>
)

data class SuggestedAddress(
    @field:Json(name = "value")
    val value: String
)

data class DaDataQuery(
    @field:Json(name = "query")
    val query: String
)
