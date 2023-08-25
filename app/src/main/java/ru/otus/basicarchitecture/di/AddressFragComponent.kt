package ru.otus.basicarchitecture.di

import dagger.Component
import ru.otus.basicarchitecture.ui.address.AddressDataFragment

@WizardScope
@Component(dependencies = [AppComponent::class])
interface AddressFragComponent {

    fun inject(fragment: AddressDataFragment)

    companion object {
        fun create(appComponent: AppComponent): AddressFragComponent {
            return DaggerAddressFragComponent.factory().create(appComponent)
        }
    }

    @Component.Factory
    interface AddressFragComponentFactory {
        fun create(appComponent: AppComponent): AddressFragComponent
    }
}