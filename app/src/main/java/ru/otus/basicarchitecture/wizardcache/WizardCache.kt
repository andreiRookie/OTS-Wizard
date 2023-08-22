package ru.otus.basicarchitecture.wizardcache

import androidx.lifecycle.LiveData
import ru.otus.basicarchitecture.dto.WizardUserData

interface WizardCache {

//    fun getUserData(): WizardUserData

    fun getData(): LiveData<WizardUserData>
    fun updateMainData(name: String, surname: String, dateOfBirth: String)
    fun updateAddress(country: String, city: String,address: String)
    fun updateInterests(interests: Set<String>)
}