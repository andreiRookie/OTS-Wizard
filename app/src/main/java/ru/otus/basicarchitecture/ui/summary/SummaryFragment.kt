package ru.otus.basicarchitecture.ui.summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.databinding.UserSummaryLayoutBinding

@AndroidEntryPoint
class SummaryFragment : Fragment() {

    private var _binding: UserSummaryLayoutBinding? = null
    private val binding: UserSummaryLayoutBinding get() = _binding!!

    private val viewModel: SummaryFragViewModel by viewModels()

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

        viewModel.state.observe(viewLifecycleOwner) { state ->
            handleState(state)
        }
    }

    private fun handleState(state: SummaryFragState) {
        binding.apply {
            namePlaceholder.text = state.name
            surnamePlaceholder.text = state.surname
            dobPlaceholder.text = state.dob
            addressPlaceholder.text = state.fullAddress

            state.interests.forEach {
                val chip = Chip(context)
                chip.isClickable = false
                chip.text = it
                interestsChipGroup.addView(chip)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}