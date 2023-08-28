package ru.otus.basicarchitecture.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.otus.basicarchitecture.wizardcache.WizardCache
import javax.inject.Singleton

@Singleton
@Component(modules = [CacheModule::class])
interface AppComponent {

    fun provideCache(): WizardCache

    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}