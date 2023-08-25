package ru.otus.basicarchitecture.di

import dagger.Component
import ru.otus.basicarchitecture.ui.summary.SummaryFragment


@WizardScope
@Component(dependencies = [AppComponent::class])
interface SummaryFragComponent {

    fun inject(fragment: SummaryFragment)

    companion object {
        fun create(appComponent: AppComponent): SummaryFragComponent {
            return DaggerSummaryFragComponent.factory().create(appComponent)
        }
    }

    @Component.Factory
    interface SummaryFragComponentFactory {
        fun create(appComponent: AppComponent): SummaryFragComponent
    }
}