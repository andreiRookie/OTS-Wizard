package ru.otus.basicarchitecture.ui.interests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.UserInterestsLayoutBinding

@AndroidEntryPoint
class InterestsDataFragment : Fragment() {

    private var _binding: UserInterestsLayoutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: InterestsFragViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UserInterestsLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initChipGroup(viewModel.getListOfInterests())

        viewModel.state.observe(viewLifecycleOwner) { state ->
            handleState(state)
        }

        binding.goToSummaryButton.setOnClickListener {
            viewModel.onSummaryButtonClick()
            findNavController().navigate(R.id.action_interestsDataFragment_to_summaryFragment)
        }
    }

    private fun handleState(state: InterestsFragState) {
       setCheckedInterests(state.checkedInterests)
    }

    private fun setCheckedInterests(checkedInterests: List<String>) {
        binding.chipGroup.children.forEach {
            it as Chip
            if (checkedInterests.contains(it.text)) {
                it.isChecked = true
            }
        }
    }
    private fun initChipGroup(listOfInterests: List<String>) {
        listOfInterests.forEach { interest ->
            val chip = Chip(this.context)
            chip.text = interest

            chip.setOnClickListener {
                if (chip.isChecked) {
                    viewModel.addInterest(chip.text.toString())
                } else {
                    viewModel.removeInterest(chip.text.toString())
                }
            }
            binding.chipGroup.addView(chip)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}