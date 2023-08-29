package ru.otus.basicarchitecture.ui.summary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.otus.basicarchitecture.wizardcache.WizardCache
import javax.inject.Inject

@HiltViewModel
class SummaryFragViewModel @Inject constructor(
    private val cache: WizardCache
) : ViewModel() {

    private val _state = MutableLiveData(SummaryFragState())
    val state: LiveData<SummaryFragState> get() = _state

    init {
        getCurrentData()
    }

    private fun getCurrentData() {
        cache.getUserData().also { data ->
            _state.value = _state.value?.copy(
                name = data.name,
                surname = data.surname,
                dob = data.dateOfBirth,
                fullAddress = concatenateAddress(data.country, data.city, data.address),
                interests = data.checkedInterests
            )
        }
    }
    private fun concatenateAddress(country: String, city: String, address: String): String {
        return "$country, $city, $address"
    }
}