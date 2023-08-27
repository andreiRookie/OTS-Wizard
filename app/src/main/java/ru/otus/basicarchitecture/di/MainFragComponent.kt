package ru.otus.basicarchitecture.di

import dagger.Component
import ru.otus.basicarchitecture.ui.main.MainDataFragment

@FragmentScope
@Component(dependencies = [ActivityComponent::class])
interface MainFragComponent {

    fun inject(fragment: MainDataFragment)

    companion object {
        fun create(activityComponent: ActivityComponent): MainFragComponent {
            return DaggerMainFragComponent.factory().create(activityComponent)
        }
    }

    @Component.Factory
    interface MainFragComponentFactory {
        fun create(activityComponent: ActivityComponent): MainFragComponent
    }
}