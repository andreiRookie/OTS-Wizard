package ru.otus.basicarchitecture.wizardcache

data class WizardUserData(
    val id: Long = 0,
    val name: String = "",
    val surname: String= "",
    val dateOfBirth: String= "",
    val country: String= "",
    val city: String= "",
    val address: String= "",
    val checkedInterests: List<String> = emptyList()
)
