package ru.otus.basicarchitecture.di

import dagger.Binds
import dagger.Module
import ru.otus.basicarchitecture.wizardcache.WizardCache
import ru.otus.basicarchitecture.wizardcache.WizardCacheImpl

@Module
interface CacheModule {

    @ActivityScope
    @Binds
    fun bindsCache(impl: WizardCacheImpl): WizardCache
}