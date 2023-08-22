package ru.otus.basicarchitecture.viewmodel


sealed interface Event {
    object OpenAddressFragment : Event
    object OpenInterestsFragment : Event
    object OpenSummaryFragment : Event
    data class Error(val message: String) : Event
}
