package ru.otus.basicarchitecture.ui.main

sealed interface MainFragEvent {
    object Empty : MainFragEvent
    data class Error(val message: String) : MainFragEvent
    object Success : MainFragEvent
}