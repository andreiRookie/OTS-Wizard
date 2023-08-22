package ru.otus.basicarchitecture.ui

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.UserDataLayoutBinding
import ru.otus.basicarchitecture.viewmodel.Action
import ru.otus.basicarchitecture.viewmodel.WizardViewModel
import ru.otus.basicarchitecture.viewmodel.Event
import ru.otus.basicarchitecture.viewmodel.State
import ru.otus.basicarchitecture.viewmodel.WizardStateHolder

class AddressDataFragment : Fragment() {

    private var _binding: UserDataLayoutBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<WizardViewModel> { WizardViewModel.getFactory(requireContext()) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UserDataLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            nameOrCountryEditText.hint = getString(R.string.country)
            surnameOrCityEditText.hint = getString(R.string.city)
            ageOrAddressEditText.hint = getString(R.string.address)
            ageOrAddressEditText.inputType = InputType.TYPE_CLASS_TEXT

            nameOrCountryEditText.addTextChangedListener(textWatcher)
            surnameOrCityEditText.addTextChangedListener(textWatcher)
            ageOrAddressEditText.addTextChangedListener(textWatcher)

            nextButton.setOnClickListener { button ->
                viewModel.sendAction(Action.AddressFragOnButtonClick(button.isSelected))
            }
        }
        setNextButtonState()

        viewModel.wizardState.observe(viewLifecycleOwner) { data ->
            binding.apply {
                nameOrCountryEditText.setText(data.country)
                surnameOrCityEditText.setText(data.city)
                ageOrAddressEditText.setText(data.address)
            }
        }

        viewModel.state.observe(viewLifecycleOwner) { stateHolder ->
//            handleStateHolder(stateHolder)
            stateHolder.event?.let { handleEvent(stateHolder.event) }
        }

    }

    private fun handleStateHolder(stateHolder: WizardStateHolder) {
        handleState(stateHolder.state)
        stateHolder.event?.let { handleEvent(stateHolder.event) }
    }

    private fun handleState(state: State) {
        when (state) {
            is State.Init -> {}
            is State.Data -> {
                val data = state.userData
                binding.apply {
                    nameOrCountryEditText.setText(data.country)
                    surnameOrCityEditText.setText(data.city)
                    ageOrAddressEditText.setText(data.address)
                }
            }
        }
    }

    private fun handleEvent(event: Event) {
        when (event) {
            is Event.Error -> {
                showToast(event.message)
            }
            is Event.OpenAddressFragment -> {}
            is Event.OpenSummaryFragment -> {}
            is Event.OpenInterestsFragment -> {
                viewModel.updateAddress(
                    binding.nameOrCountryEditText.text.toString(),
                    binding.surnameOrCityEditText.text.toString(),
                    binding.ageOrAddressEditText.text.toString(),
                )
                findNavController().navigate(R.id.action_addressDataFragment_to_interestsDataFragment)
            }
        }
    }

    private fun setNextButtonState() {
        binding.apply {
            nextButton.isSelected = ageOrAddressEditText.text.isNotEmpty()
                    && nameOrCountryEditText.text.isNotEmpty()
                    && surnameOrCityEditText.text.isNotEmpty()
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) { setNextButtonState() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToast(msg: String) {
        Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show()
    }
}