package ru.otus.basicarchitecture.ui.main

import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.UserDataLayoutBinding
import ru.otus.basicarchitecture.util.EditTextDateMask
import ru.otus.basicarchitecture.util.WizardTextWatcher

@AndroidEntryPoint
class MainDataFragment : Fragment() {

    private var _binding: UserDataLayoutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainFragViewModel by viewModels()

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
            nameOrCountryEditText.hint = getString(R.string.name)
            surnameOrCityEditText.hint = getString(R.string.surname)
            ageOrAddressEditText.hint = getString(R.string.age)

            WizardTextWatcher(nameOrCountryEditText).startListen { setButtonState() }
            WizardTextWatcher(surnameOrCityEditText).startListen { setButtonState() }
            WizardTextWatcher(ageOrAddressEditText).startListen { setButtonState() }
            EditTextDateMask(ageOrAddressEditText).startListen()

            ageOrAddressEditText.inputType = InputType.TYPE_CLASS_DATETIME
            ageOrAddressEditText.filters = arrayOf(InputFilter.LengthFilter(10))

            ageOrAddressEditText.setOnFocusChangeListener { _, hasFocus ->
                ageOrAddressEditText.hint = if (hasFocus) getString(R.string.dd_mm_yyyy_hint) else getString(R.string.age)
            }

            nextButton.setOnClickListener {
                viewModel.onNextButtonClick(
                    nextButton.isSelected, ageOrAddressEditText.text.toString()
                )
            }
        }

        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            handleState(state)
        }
        viewModel.viewEvent.observe(viewLifecycleOwner) { event ->
            handleEvent(event)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCurrentData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleState(state: MainFragState) {
        binding.apply {
            nameOrCountryEditText.setText(state.name)
            surnameOrCityEditText.setText(state.surname)
            ageOrAddressEditText.setText(state.dob)
            nextButton.isSelected = state.isButtonEnabled
        }
    }

    private fun handleEvent(event: MainFragEvent) {
        when (event) {
            is MainFragEvent.Error -> {
                showToast(event.message)
                viewModel.onEventHandled()
            }

            is MainFragEvent.Success -> {
                viewModel.updateMainData(
                    binding.nameOrCountryEditText.text.toString(),
                    binding.surnameOrCityEditText.text.toString(),
                    binding.ageOrAddressEditText.text.toString(),
                )
                findNavController().navigate(R.id.action_mainDataFragment_to_addressDataFragment)
                viewModel.onEventHandled()
            }

            is MainFragEvent.Empty -> {}
        }
    }

    private fun setButtonState() {
        binding.apply {
            nextButton.isSelected = ageOrAddressEditText.text.isNotEmpty()
                    && nameOrCountryEditText.text.isNotEmpty()
                    && surnameOrCityEditText.text.isNotEmpty()
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(
            this.context,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }
}