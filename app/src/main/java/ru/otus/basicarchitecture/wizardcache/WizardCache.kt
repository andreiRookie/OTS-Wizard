package ru.otus.basicarchitecture.wizardcache


interface WizardCache {

    fun getUserData(): WizardUserData
    fun getInterestsList(): List<String>
    fun updateMainData(name: String, surname: String, dateOfBirth: String)
    fun updateAddress(address: String)
    fun updateInterests(interests: List<String>)
}