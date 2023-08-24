package ru.otus.basicarchitecture.ui.address

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.UserDataLayoutBinding
import ru.otus.basicarchitecture.di.appComponent
import ru.otus.basicarchitecture.util.WizardTextWatcher
import javax.inject.Inject

class AddressDataFragment : Fragment() {

    private var _binding: UserDataLayoutBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: AddressFragViewModel

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

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

            WizardTextWatcher(nameOrCountryEditText).startListen { setButtonState() }
            WizardTextWatcher(surnameOrCityEditText).startListen { setButtonState() }
            WizardTextWatcher(ageOrAddressEditText).startListen { setButtonState() }

            nextButton.setOnClickListener {
                viewModel.onNextButtonClick(nextButton.isSelected)
            }
        }

        viewModel.getCurrentData()

        viewModel.state.observe(viewLifecycleOwner) { state ->
            handleState(state)
        }
        viewModel.event.observe(viewLifecycleOwner) { event ->
            handleEvent(event)
        }
    }

    private fun handleState(state: AddressFragState) {
        binding.apply {
            nameOrCountryEditText.setText(state.country)
            surnameOrCityEditText.setText(state.city)
            ageOrAddressEditText.setText(state.address)
            nextButton.isSelected = state.isButtonEnabled
        }
    }

    private fun handleEvent(event: AddressFragEvent) {
        when (event) {
            is AddressFragEvent.Error -> {
                showToast(event.message)
            }
            is AddressFragEvent.Success -> {
                viewModel.updateAddressData(
                    binding.nameOrCountryEditText.text.toString(),
                    binding.surnameOrCityEditText.text.toString(),
                    binding.ageOrAddressEditText.text.toString(),
                )
                findNavController().navigate(R.id.action_addressDataFragment_to_interestsDataFragment)
            }
            is AddressFragEvent.Empty -> {}
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
        Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}