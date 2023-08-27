package ru.otus.basicarchitecture.di

import dagger.Component
import ru.otus.basicarchitecture.ui.interests.InterestsDataFragment

@FragmentScope
@Component(dependencies = [ActivityComponent::class])
interface InterestsFragComponent {

    fun inject(fragment: InterestsDataFragment)

    companion object {
        fun create(activityComponent:ActivityComponent): InterestsFragComponent {
            return DaggerInterestsFragComponent.factory().create(activityComponent)
        }
    }

    @Component.Factory
    interface InterestsFragComponentFactory {
        fun create(activityComponent: ActivityComponent): InterestsFragComponent
    }
}