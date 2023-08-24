package ru.otus.basicarchitecture.ui.main

data class MainFragState(
    val name: String = "",
    val surname: String = "",
    val dob: String = "",
    val isButtonEnabled: Boolean = false
)