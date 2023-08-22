package ru.otus.basicarchitecture.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.otus.basicarchitecture.dto.WizardUserData
import ru.otus.basicarchitecture.util.AgeValidator
import ru.otus.basicarchitecture.wizardcache.WizardCache
import ru.otus.basicarchitecture.wizardcache.WizardCacheImpl

class WizardViewModel(
    private val cache: WizardCache
) : ViewModel() {

    private val _state = MutableLiveData(WizardStateHolder(state = State.Init))
    val state: LiveData<WizardStateHolder> get() = _state

    val wizardState: LiveData<WizardUserData> = cache.getData()


    fun sendAction(action: Action) {
        when (action) {
            is Action.MainFragOnButtonClick -> {
                openAddressFragment(action.isButtonSelected, action.dob)
                onEventHandled()
            }
            is Action.AddressFragOnButtonClick -> {
                openInterestsFragment(action.isButtonSelected)
                onEventHandled()
            }
            is Action.InterestsFragOnButtonClick -> {
                openSummaryFragment()
                onEventHandled()
            }
        }
    }

//    private fun getUserData() {
//        val currData = cache.getUserData()
//        _state.value = _state.value?.copy(state = State.Data(currData))
//    }


    fun updateMainData(name: String, surname: String, dob: String) {
        cache.updateMainData(name, surname, dob)

//        getUserData()
    }

    fun updateAddress(country: String, city: String, address: String) {
        cache.updateAddress(country, city, address)

//        getUserData()
    }

    fun updateInterests(interests: Set<String>) {
        cache.updateInterests(interests)

//        getUserData()
    }

    private fun openAddressFragment(isButtonSelected: Boolean, dob: String) {
        if (!isButtonSelected) {
            _state.value = _state.value?.copy(event = Event.Error("Enter name, surname, age"))
            return
        }

        if (AgeValidator.isAgeValid(dob)) {
            _state.value = _state.value?.copy(event = Event.OpenAddressFragment)
        } else {
            _state.value = _state.value?.copy(event = Event.Error("Too young to proceed"))
        }
    }

    private fun openInterestsFragment(isButtonSelected: Boolean) {
        if (isButtonSelected) {
            _state.value = _state.value?.copy(event = Event.OpenInterestsFragment)
        } else {
            _state.value = _state.value?.copy(event = Event.Error("Enter country, city, address"))
        }
    }

    private fun openSummaryFragment() {
        _state.value = _state.value?.copy(event = Event.OpenSummaryFragment)
    }

    private fun onEventHandled() {
//        if (_state.value?.event == event) {
            _state.value = _state.value?.copy(event = null)
//        }
    }

    companion object{
        fun getFactory(context: Context) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return WizardViewModel(WizardCacheImpl()) as T
            }
        }
    }
}