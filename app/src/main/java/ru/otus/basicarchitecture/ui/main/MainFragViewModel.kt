package ru.otus.basicarchitecture.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.util.AgeValidator
import ru.otus.basicarchitecture.wizardcache.WizardCache
import javax.inject.Inject

@HiltViewModel
class MainFragViewModel @Inject constructor(
    private val cache: WizardCache
) : ViewModel() {

    private val _viewState = MutableLiveData(MainFragState())
    val viewState: LiveData<MainFragState> get() = _viewState

    private val _viewEvent = MutableLiveData<MainFragEvent>()
    val viewEvent: LiveData<MainFragEvent> get() = _viewEvent


    init {
        getCurrentData()
    }

    fun getCurrentData() {
        cache.getUserData().also { data ->
            _viewState.value = _viewState.value?.copy(
                name = data.name,
                surname = data.surname,
                dob = data.dateOfBirth,
                isButtonEnabled = data.name.isNotEmpty()
                        && data.surname.isNotEmpty()
                        && data.dateOfBirth.isNotEmpty()
            )
        }
    }

    fun updateMainData(name: String, surname: String, dob: String) {
        cache.updateMainData(name, surname, dob)
    }

    fun onNextButtonClick(isButtonSelected: Boolean, dob: String) {
        if (!isButtonSelected) {
            _viewEvent.value = MainFragEvent.Error("Enter name, surname, age")
            return
        }

        if (AgeValidator.isAgeValid(dob)) {
            _viewEvent.value = MainFragEvent.Success
        } else {
            _viewEvent.value = MainFragEvent.Error("Too young to proceed")
        }
    }

    fun onEventHandled() {
        _viewEvent.value = MainFragEvent.Empty
    }
}