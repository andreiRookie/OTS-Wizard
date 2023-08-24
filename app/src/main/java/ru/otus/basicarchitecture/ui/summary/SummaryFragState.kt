package ru.otus.basicarchitecture.ui.summary

data class SummaryFragState(
    val name: String ="",
    val surname: String = "",
    val dob: String = "",
    val fullAddress: String = "",
    val interests: List<String> = emptyList()
)