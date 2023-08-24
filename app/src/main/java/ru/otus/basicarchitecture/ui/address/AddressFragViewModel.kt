package ru.otus.basicarchitecture.ui.address

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.wizardcache.WizardCache
import javax.inject.Inject

class AddressFragViewModel @Inject constructor(
    private val cache: WizardCache
) : ViewModel() {

    private val _state = MutableLiveData(AddressFragState())
    val state: LiveData<AddressFragState> get() = _state

    private val _event = MutableLiveData<AddressFragEvent>()
    val event: LiveData<AddressFragEvent> get() = _event

    init {
        getCurrentData()
    }

    fun getCurrentData() {
        cache.getUserData().also { data ->
            _state.value = _state.value?.copy(
                country = data.country,
                city = data.city,
                address = data.address,
                isButtonEnabled = data.country.isNotEmpty()
                        && data.city.isNotEmpty()
                        && data.address.isNotEmpty()
            )
        }
    }

    fun updateAddressData(country: String, city: String, address: String) {
        cache.updateAddress(country, city, address)
    }

    fun onNextButtonClick(isButtonEnabled: Boolean) {
        if (isButtonEnabled) {
            _event.value = AddressFragEvent.Success
            onEventHandled()
        } else {
            _event.value = AddressFragEvent.Error("Enter country, city, address")
            onEventHandled()
        }
    }

    private fun onEventHandled() {
        _event.value = AddressFragEvent.Empty
    }
}