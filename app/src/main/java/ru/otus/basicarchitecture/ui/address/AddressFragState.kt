package ru.otus.basicarchitecture.ui.address

data class AddressFragState(
    val country: String = "",
    val city: String = "",
    val address: String = "",
    val isButtonEnabled: Boolean = false
)