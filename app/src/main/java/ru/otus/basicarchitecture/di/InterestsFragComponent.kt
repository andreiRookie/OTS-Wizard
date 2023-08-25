package ru.otus.basicarchitecture.di

import dagger.Component
import ru.otus.basicarchitecture.ui.interests.InterestsDataFragment

@WizardScope
@Component(dependencies = [AppComponent::class])
interface InterestsFragComponent {

    fun inject(fragment: InterestsDataFragment)

    @Component.Factory
    interface InterestsFragComponentFactory {
        fun create(appComponent: AppComponent): InterestsFragComponent
    }

    companion object {
        fun create(appComponent: AppComponent): InterestsFragComponent {
            return DaggerInterestsFragComponent.factory().create(appComponent)
        }
    }
}