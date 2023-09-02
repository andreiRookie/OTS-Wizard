package ru.otus.basicarchitecture.domain

import ru.otus.basicarchitecture.network.DaDataRepository
import javax.inject.Inject

class GetAddressSuggestionUseCase @Inject constructor(
    private val networkRepo: DaDataRepository
) {

    suspend fun execute(query: String): List<String> {
        return networkRepo.loadSuggestedAddresses(query)
    }
}