package ru.otus.basicarchitecture.ui.address

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.util.SingleLiveEvent
import ru.otus.basicarchitecture.wizardcache.WizardCache
import javax.inject.Inject

@HiltViewModel
class AddressFragViewModel @Inject constructor(
    private val cache: WizardCache
) : ViewModel() {

    private val _state = MutableLiveData(AddressFragState())
    val state: LiveData<AddressFragState> get() = _state

    private val _navigateToInterestsFrag = SingleLiveEvent<Unit>()
    val navigateToInterestsFrag: LiveData<Unit> get() = _navigateToInterestsFrag

    init {
        getCurrentData()
    }

    fun getCurrentData() {
        cache.getUserData().also { data ->
            _state.value = _state.value?.copy(address = data.address)
        }
    }

    fun openInterestsFragment() {
        _navigateToInterestsFrag.call()
    }

    fun updateAddressData(address: String) {
        cache.updateAddress(address)
    }
}