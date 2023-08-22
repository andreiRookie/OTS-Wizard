package ru.otus.basicarchitecture.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import ru.otus.basicarchitecture.R
import ru.otus.basicarchitecture.databinding.UserInterestsLayoutBinding
import ru.otus.basicarchitecture.viewmodel.Action
import ru.otus.basicarchitecture.viewmodel.Event
import ru.otus.basicarchitecture.viewmodel.State
import ru.otus.basicarchitecture.viewmodel.WizardViewModel
import ru.otus.basicarchitecture.viewmodel.WizardStateHolder

class InterestsDataFragment : Fragment() {

    private var _binding: UserInterestsLayoutBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<WizardViewModel> { WizardViewModel.getFactory(requireContext()) }

    private val checkedInterests = mutableSetOf<String>()


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

        Log.e("AAA", "checkedInterests" + checkedInterests)

//        viewModel.wizardState.observe(viewLifecycleOwner) { data ->
//            if (data.interests.isNotEmpty()) {
//                checkedInterests += data.interests
//            }
//        }

        initChipGroup()

//        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
//            checkedIds.forEach {
//                val chip = group.findViewById<Chip>(it)
//                checkedInterests += chip.text.toString()
//            }
//        }

        viewModel.state.observe(viewLifecycleOwner) { stateHolder ->
//            handleStateHolder(stateHolder)
            stateHolder.event?.let { handleEvent(stateHolder.event) }
        }

        binding.goToSummaryButton.setOnClickListener {
            viewModel.sendAction(Action.InterestsFragOnButtonClick)
        }

        checkState()
    }

    private fun checkState() {
        viewModel.wizardState.observe(viewLifecycleOwner) { data ->
            if (data.interests.isNotEmpty()) {
                checkedInterests += data.interests
            }
        }
    }

    private fun initChipGroup() {
        INTERESTS.forEach { interest ->
            val chip = Chip(this.context)
            chip.text = interest

            if (checkedInterests.contains(interest)) {
                chip.isChecked = true
            }

            chip.setOnClickListener {
                if (chip.isChecked) {
                    checkedInterests += chip.text.toString()
                } else {
                    checkedInterests -= chip.text.toString()
                }
            }
            binding.chipGroup.addView(chip)
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
                if (data.interests.isNotEmpty()) {
                    checkedInterests += data.interests
                }
            }
        }
    }

    private fun handleEvent(event: Event) {
        when (event) {
            is Event.Error -> {}
            is Event.OpenAddressFragment -> {}
            is Event.OpenSummaryFragment -> {
                viewModel.updateInterests(checkedInterests)
                findNavController().navigate(R.id.action_interestsDataFragment_to_summaryFragment)
            }
            is Event.OpenInterestsFragment -> {}
        }
    }

    companion object {
        private val INTERESTS = listOf(
            "Hiking", "Programming", "Travel", "Reading books", "Playing double bass",
            "Movies", "Cycling", "Theatre", "Running", "Photography", "Bouldering", "Nightclubs"
        )
    }
}