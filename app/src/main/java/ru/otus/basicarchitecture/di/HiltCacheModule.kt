package ru.otus.basicarchitecture.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import ru.otus.basicarchitecture.wizardcache.WizardCache
import ru.otus.basicarchitecture.wizardcache.WizardCacheImpl


@Module
@InstallIn(ActivityRetainedComponent::class)
interface HiltCacheModule {

    @Binds
    @ActivityRetainedScoped
    fun bindsCache(impl: WizardCacheImpl): WizardCache
}