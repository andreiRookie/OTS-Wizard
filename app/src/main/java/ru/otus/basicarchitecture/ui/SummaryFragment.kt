package ru.otus.basicarchitecture.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.chip.Chip
import ru.otus.basicarchitecture.databinding.UserSummaryLayoutBinding
import ru.otus.basicarchitecture.viewmodel.State
import ru.otus.basicarchitecture.viewmodel.WizardViewModel

class SummaryFragment : Fragment() {

    private var _binding: UserSummaryLayoutBinding? = null
    private val binding: UserSummaryLayoutBinding get() = _binding!!

    private val viewModel by activityViewModels<WizardViewModel> { WizardViewModel.getFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UserSummaryLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.wizardState.observe(viewLifecycleOwner) { data ->
            binding.apply {
                namePlaceholder.text = data.name
                surnamePlaceholder.text = data.surname
                dobPlaceholder.text = data.dateOfBirth
                addressPlaceholder.text = concatenateAddress(
                    data.country, data.city, data.address)


                data.interests.forEach {
                    val chip = Chip(context)
                    chip.isClickable = false
                    chip.text = it
                    interestsChipGroup.addView(chip)
                }
            }
        }

//        viewModel.state.observe(viewLifecycleOwner) { stateHolder ->
//            handleState(stateHolder.state)
//        }
    }

    private fun handleState(state: State) {
        when (state) {
            is State.Init -> {}
            is State.Data -> {
                val data = state.userData
                binding.apply {
                    namePlaceholder.text = data.name
                    surnamePlaceholder.text = data.surname
                    dobPlaceholder.text = data.dateOfBirth
                    addressPlaceholder.text = concatenateAddress(
                        data.country, data.city, data.address)


                    data.interests.forEach {
                        val chip = Chip(context)
                        chip.isClickable = false
                        chip.text = it
                        interestsChipGroup.addView(chip)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun concatenateAddress(country: String, city: String, address: String): String {
        return "$country, $city, $address"
    }
}