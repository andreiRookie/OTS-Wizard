package ru.otus.basicarchitecture.ui.interests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.otus.basicarchitecture.wizardcache.WizardCache
import javax.inject.Inject

class InterestsFragViewModel @Inject constructor(
    private val cache: WizardCache
) : ViewModel() {

    private val _state = MutableLiveData(InterestsFragState())
    val state: LiveData<InterestsFragState> get() = _state

    init {
        _state.value = _state.value?.copy(checkedInterests = cache.getUserData().checkedInterests)
    }

    fun addInterest(interest: String) {
        val newCheckedInterests = _state.value?.let { it.checkedInterests + interest } ?: emptyList()
        _state.value = _state.value?.copy(checkedInterests = newCheckedInterests)
    }

    fun removeInterest(interest: String) {
        val newCheckedInterests = _state.value?.let { it.checkedInterests - interest } ?: emptyList()
        _state.value = _state.value?.copy(checkedInterests = newCheckedInterests)
    }

    fun getListOfInterests(): List<String> = cache.getInterestsList()

    fun onSummaryButtonClick() {
        _state.value?.let { cache.updateInterests(it.checkedInterests) }
    }
}