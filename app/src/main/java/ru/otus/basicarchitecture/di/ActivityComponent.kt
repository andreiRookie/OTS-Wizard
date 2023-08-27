package ru.otus.basicarchitecture.di

import dagger.BindsInstance
import dagger.Component
import ru.otus.basicarchitecture.MainActivity
import ru.otus.basicarchitecture.wizardcache.WizardCache

@ActivityScope
@Component(modules = [CacheModule::class],
dependencies = [AppComponent::class])
interface ActivityComponent {

    fun provideCache(): WizardCache
    fun activity(): MainActivity
    fun inject(activity: MainActivity)

    @Component.Factory
    interface MainActivityComponentFactory {
        fun create(appComponent: AppComponent, @BindsInstance activity: MainActivity): ActivityComponent
    }
}