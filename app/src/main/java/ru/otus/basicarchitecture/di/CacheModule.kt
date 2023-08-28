package ru.otus.basicarchitecture.di

import dagger.Binds
import dagger.Module
import ru.otus.basicarchitecture.wizardcache.WizardCache
import ru.otus.basicarchitecture.wizardcache.WizardCacheImpl
import javax.inject.Singleton

@Module
interface CacheModule {

    @Singleton
    @Binds
    fun bindsCache(impl: WizardCacheImpl): WizardCache
}