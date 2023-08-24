package ru.otus.basicarchitecture.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.otus.basicarchitecture.ui.address.AddressDataFragment
import ru.otus.basicarchitecture.ui.interests.InterestsDataFragment
import ru.otus.basicarchitecture.ui.main.MainDataFragment
import ru.otus.basicarchitecture.ui.summary.SummaryFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [CacheModule::class])
interface AppComponent {

    fun inject(fragment: MainDataFragment)
    fun inject(fragment: AddressDataFragment)
    fun inject(fragment: InterestsDataFragment)
    fun inject(fragment: SummaryFragment)

    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}