package ru.otus.basicarchitecture.di

import dagger.Component
import ru.otus.basicarchitecture.ui.address.AddressDataFragment

@FragmentScope
@Component(dependencies = [ActivityComponent::class])
interface AddressFragComponent {

    fun inject(fragment: AddressDataFragment)

    companion object {
        fun create(activityComponent: ActivityComponent): AddressFragComponent {
            return DaggerAddressFragComponent.factory().create(activityComponent)
        }
    }

    @Component.Factory
    interface AddressFragComponentFactory {
        fun create(activityComponent: ActivityComponent): AddressFragComponent
    }
}