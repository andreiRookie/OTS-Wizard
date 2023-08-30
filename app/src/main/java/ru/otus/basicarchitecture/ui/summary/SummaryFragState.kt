package ru.otus.basicarchitecture.ui.summary

data class SummaryFragState(
    val name: String ="",
    val surname: String = "",
    val dob: String = "",
    val address: String = "",
    val interests: List<String> = emptyList()
)