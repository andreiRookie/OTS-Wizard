package ru.otus.basicarchitecture.viewmodel

import ru.otus.basicarchitecture.dto.WizardUserData

sealed interface State {

    data class Data(val userData: WizardUserData) : State
    object Init : State
}
