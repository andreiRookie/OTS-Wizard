package ru.otus.basicarchitecture.dto

data class WizardUserData(
    val id: Long = 0,
    val name: String = "",
    val surname: String= "",
    val dateOfBirth: String= "",
    val country: String= "",
    val city: String= "",
    val address: String= "",
    val interests: Set<String> = emptySet()
)
