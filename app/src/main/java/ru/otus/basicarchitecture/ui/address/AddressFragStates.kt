package ru.otus.basicarchitecture.ui.address

data class AddressFragState(
    val address: String = ""
)

data class AddressSuggestionState(
    val suggestions: List<String> = emptyList()
)