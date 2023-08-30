package ru.otus.basicarchitecture.ui.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.UserAddressLayoutBinding
import ru.otus.basicarchitecture.util.WizardTextWatcher

@AndroidEntryPoint
class AddressDataFragment : Fragment() {

    private var _binding: UserAddressLayoutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddressFragViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UserAddressLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            addressEditText.hint = getString(R.string.address)
            WizardTextWatcher(addressEditText).startListen { setButtonState() }

            nextButton.setOnClickListener {
                if (!it.isSelected) {
                    showToast("Enter address")
                    return@setOnClickListener
                }
                viewModel.openInterestsFragment()
            }
        }

        viewModel.getCurrentData()

        viewModel.state.observe(viewLifecycleOwner) { state ->
            handleState(state)
        }

        viewModel.navigateToInterestsFrag.observe(viewLifecycleOwner) {
            viewModel.updateAddressData(binding.addressEditText.text.toString())
            findNavController().navigate(R.id.action_addressDataFragment_to_interestsDataFragment)
        }
    }

    private fun handleState(state: AddressFragState) {
        binding.apply {
            addressEditText.setText(state.address)
        }
    }

    private fun setButtonState() {
        binding.apply {
            nextButton.isSelected = addressEditText.text.isNotEmpty()
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