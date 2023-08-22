package ru.otus.basicarchitecture.ui

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
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

class MainDataFragment : Fragment() {

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
            nameOrCountryEditText.hint = getString(R.string.name)
            surnameOrCityEditText.hint = getString(R.string.surname)
            ageOrAddressEditText.hint = getString(R.string.age)

            nameOrCountryEditText.addTextChangedListener(textWatcher)
            surnameOrCityEditText.addTextChangedListener(textWatcher)
            ageOrAddressEditText.addTextChangedListener(ageFieldTextWatcher)

            ageOrAddressEditText.inputType = InputType.TYPE_CLASS_DATETIME
            ageOrAddressEditText.filters = arrayOf(InputFilter.LengthFilter(10))

            ageOrAddressEditText.setOnFocusChangeListener { _, hasFocus ->
                ageOrAddressEditText.hint = if (hasFocus) {
                    getString(R.string.dd_mm_yyyy_hint)
                } else {
                    getString(R.string.age)
                }
            }

            setNextButtonState()

            nextButton.setOnClickListener { button ->
                viewModel.sendAction(
                    Action.MainFragOnButtonClick(
                        button.isSelected,
                        ageOrAddressEditText.text.toString()
                    )
                )
            }
        }
        viewModel.wizardState.observe(viewLifecycleOwner) { user ->
            binding.apply {
                nameOrCountryEditText.setText(user.name)
                surnameOrCityEditText.setText(user.surname)
                ageOrAddressEditText.setText(user.dateOfBirth)
            }
        }

        viewModel.state.observe(viewLifecycleOwner) { stateHolder ->
//            handleStateHolder(stateHolder
            stateHolder.event?.let { handleEvent(stateHolder.event) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
                    nameOrCountryEditText.setText(data.name)
                    surnameOrCityEditText.setText(data.surname)
                    ageOrAddressEditText.setText(data.dateOfBirth)
                }
            }
        }
    }

    private fun handleEvent(event: Event) {
        when (event) {
            is Event.Error -> {
                showToast(event.message)
            }

            is Event.OpenAddressFragment -> {
                viewModel.updateMainData(
                    binding.nameOrCountryEditText.text.toString(),
                    binding.surnameOrCityEditText.text.toString(),
                    binding.ageOrAddressEditText.text.toString(),
                )
                findNavController().navigate(R.id.action_mainDataFragment_to_addressDataFragment)
            }

            is Event.OpenInterestsFragment -> {}
            is Event.OpenSummaryFragment -> {}
        }
    }

    private fun setNextButtonState() {
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

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            setNextButtonState()
        }
    }

    private val ageFieldTextWatcher = object : TextWatcher {
        val firstDividerPosition = 2
        val secondDividerPosition = 5
        val maxTextLength = 10

        var isEdited = false
        val divider = "."

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (isEdited) {
                isEdited = false
                return
            }

            var currText = getText()

            currText = handleDateDivider(currText, firstDividerPosition, start, before)
            currText = handleDateDivider(currText, secondDividerPosition, start, before)

            isEdited = true
            binding.ageOrAddressEditText.setText(currText)
            binding.ageOrAddressEditText.setSelection(binding.ageOrAddressEditText.text.length)
        }

        private fun getText(): String {
            return if (binding.ageOrAddressEditText.text.length >= maxTextLength)
                binding.ageOrAddressEditText.text.toString().substring(0, maxTextLength)
            else
                binding.ageOrAddressEditText.text.toString()
        }

        private fun handleDateDivider(
            currText: String,
            dividerPosition: Int,
            start: Int,
            before: Int
        ): String {
            if (currText.length == dividerPosition) {
                return if (before <= dividerPosition && start < dividerPosition)
                    currText + divider
                else
                    currText.dropLast(1)
            }
            return currText
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(s: Editable?) {
            setNextButtonState()
        }
    }
}