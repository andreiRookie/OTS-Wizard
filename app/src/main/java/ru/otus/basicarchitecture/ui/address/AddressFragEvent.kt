package ru.otus.basicarchitecture.ui.address

sealed interface AddressFragEvent {
    object Empty : AddressFragEvent
    data class Error(val message: String) : AddressFragEvent
    object Success : AddressFragEvent
}