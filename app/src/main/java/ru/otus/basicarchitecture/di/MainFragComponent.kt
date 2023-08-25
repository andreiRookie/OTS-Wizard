package ru.otus.basicarchitecture.di

import dagger.Component
import ru.otus.basicarchitecture.ui.main.MainDataFragment

@WizardScope
@Component(dependencies = [AppComponent::class])
interface MainFragComponent {

    fun inject(fragment: MainDataFragment)

    companion object {
        fun create(appComponent: AppComponent): MainFragComponent {
            return DaggerMainFragComponent.factory().create(appComponent)
        }
    }

    @Component.Factory
    interface MainFragComponentFactory {
        fun create(appComponent: AppComponent): MainFragComponent
    }
}