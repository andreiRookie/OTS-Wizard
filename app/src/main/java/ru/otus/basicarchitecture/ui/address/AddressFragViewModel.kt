package ru.otus.basicarchitecture.ui.address

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.otus.basicarchitecture.domain.GetAddressSuggestionUseCase
import ru.otus.basicarchitecture.util.SingleLiveEvent
import ru.otus.basicarchitecture.wizardcache.WizardCache
import javax.inject.Inject

@HiltViewModel
class AddressFragViewModel @Inject constructor(
    private val cache: WizardCache,
    private val useCase: GetAddressSuggestionUseCase
) : ViewModel() {

    private val _state = MutableLiveData(AddressFragState())
    val state: LiveData<AddressFragState> get() = _state

    private val _suggestionState = MutableLiveData(AddressSuggestionState())
    val suggestionState: LiveData<AddressSuggestionState> get() = _suggestionState

    private val _navigateToInterestsFrag = SingleLiveEvent<Unit>()
    val navigateToInterestsFrag: LiveData<Unit> get() = _navigateToInterestsFrag

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.Main.immediate + viewModelJob)


    init {
        getCurrentData()
    }

    fun loadSuggestedAddresses(query: String) {
        viewModelScope.launch {
            try {
                val list = useCase.execute(query)
                _suggestionState.value =
                    _suggestionState.value?.copy(suggestions = list)

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getCurrentData() {
        cache.getUserData().also { data ->
            _state.value =
                _state.value?.copy(address = data.address)
        }
    }

    fun openInterestsFragment() {
        _navigateToInterestsFrag.call()
    }

    fun updateAddressData(address: String) {
        cache.updateAddress(address)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}