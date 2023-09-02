package ru.otus.basicarchitecture.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.otus.basicarchitecture.util.SuggestionsMapper
import javax.inject.Inject

interface DaDataRepository {
    suspend fun loadSuggestedAddresses(query: String): List<String>
}

class DaDataRepositoryImpl @Inject constructor(
    private val service: DaDataService,
    private val dispatcher: CoroutineDispatcher
) : DaDataRepository {

    private val mapper = SuggestionsMapper

    override suspend fun loadSuggestedAddresses(query: String): List<String> {
        return withContext(dispatcher) {
            service.getAddressSuggestions(DaDataQuery(query = query)).let { response ->
                mapper.mapFromEntityList(response.suggestions)
            }
        }
    }
}