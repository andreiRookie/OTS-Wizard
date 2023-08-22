package ru.otus.basicarchitecture.viewmodel

sealed interface Action {
    data class MainFragOnButtonClick(val isButtonSelected: Boolean, val dob: String) : Action
    data class AddressFragOnButtonClick(val isButtonSelected: Boolean) : Action
    object InterestsFragOnButtonClick : Action
}