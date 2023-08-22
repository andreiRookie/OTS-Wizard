package ru.otus.basicarchitecture.viewmodel

data class WizardStateHolder(
    val state: State,
    val event: Event? = null
)