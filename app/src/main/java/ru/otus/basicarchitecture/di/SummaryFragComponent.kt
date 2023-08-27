package ru.otus.basicarchitecture.di

import dagger.Component
import ru.otus.basicarchitecture.ui.summary.SummaryFragment


@FragmentScope
@Component(dependencies = [ActivityComponent::class])
interface SummaryFragComponent {

    fun inject(fragment: SummaryFragment)

    companion object {
        fun create(activityComponent: ActivityComponent): SummaryFragComponent {
            return DaggerSummaryFragComponent.factory().create(activityComponent)
        }
    }

    @Component.Factory
    interface SummaryFragComponentFactory {
        fun create(activityComponent: ActivityComponent): SummaryFragComponent
    }
}