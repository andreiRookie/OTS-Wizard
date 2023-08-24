package ru.otus.basicarchitecture.di

import android.app.Application
import android.content.Context

class WizardApp : Application() {

    private var _appComponent: AppComponent? = null
    internal val appComponent: AppComponent
        get() = checkNotNull(_appComponent) {
            "AppComponent is null"
        }

    override fun onCreate() {
        super.onCreate()
        _appComponent = DaggerAppComponent.factory().create(this)
    }
}

val Context.appComponent: AppComponent
    get() =
        when (this) {
            is WizardApp -> appComponent
            else -> this.applicationContext.appComponent
        }